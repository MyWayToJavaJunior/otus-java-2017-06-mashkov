package ru.otus.executor;

import ru.otus.exceptions.MappingException;
import ru.otus.models.DataSet;
import ru.otus.testFramework.ReflectionHelper;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Executor {
    private Connection connection;

    public Executor(Connection connection){
        this.connection = connection;
    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }

    public <T> T execQuery(String query, TResultHandler<T> handler) throws SQLException{
        try (Statement stmt = connection.createStatement()){

            ResultSet resultSet = stmt.executeQuery(query);
            T obj = handler.handle(resultSet);
            return obj;
        }
    }

    public <T extends DataSet> void save(T user) throws SQLException, MappingException {
        try (Statement stmt = connection.createStatement()){


            StringBuilder builder = new StringBuilder();
            StringBuilder nameBuilder = new StringBuilder();
            StringBuilder valueBuilder = new StringBuilder();
            builder.append("insert into ");


            Annotation tableAnnotation = user.getClass().getAnnotation(Table.class);
            if (tableAnnotation!=null){
                builder.append(((Table)tableAnnotation).name());
            } else throw new MappingException("Не могу сохранить объект. Нет названия таблицы!");


            Field[] fields = ReflectionHelper.getFields(user);

            for (int i = 0, fieldsLength = fields.length; i < fieldsLength; i++) {
                Field field = fields[i];
                Annotation annotation = field.getAnnotation(Column.class);
                if (annotation != null) {
                    String columnName = ((Column) annotation).name();
                    Object value = ReflectionHelper.getFieldValue(user, field.getName());

                    nameBuilder.append(columnName);
                    if (value instanceof String) {
                        valueBuilder.append("'").append(value).append("'");
                    } else valueBuilder.append(value);

                    if (i != fieldsLength-1) {
                        nameBuilder.append(", ");
                        valueBuilder.append(", ");
                    }

                }
                System.out.println(annotation);
            }

            builder.append("(")
                    .append(nameBuilder)
                    .append(")")
                    .append(" values ")
                    .append("(")
                    .append(valueBuilder)
                    .append(")");

            System.out.println("Executing: "+builder.toString());
            int result = stmt.executeUpdate(builder.toString(), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            long id = rs.getLong(1);
            user.setId(id);
            System.out.println("Updated: "+result+", ID="+id);

        }
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws MappingException, SQLException {
        Field[] fields = ReflectionHelper.getFields(clazz);

        Annotation tableAnnotation = clazz.getAnnotation(Table.class);

        if (tableAnnotation!=null){

            StringBuilder builder = new StringBuilder();
            builder.append("select * from ").append(((Table)tableAnnotation).name()).append(" where id=")
                    .append(id);
            T result = execQuery(builder.toString(), resultSet -> {
                return null;
            });

        } else {
            throw new MappingException("Не могу сохранить объект. Нет названия таблицы!");
        }
    }


}
