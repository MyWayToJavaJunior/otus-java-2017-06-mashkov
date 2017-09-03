package ru.otus.executor;

import ru.otus.exceptions.MappingException;
import ru.otus.models.DataSet;
import ru.otus.testFramework.ReflectionHelper;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Executor {
    private Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;

    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }

    public <T> T execQuery(String query, TResultHandler<T> handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {

            ResultSet resultSet = stmt.executeQuery(query);
            T obj = handler.handle(resultSet);
            return obj;
        }
    }

    public <T extends DataSet> void save(T user) throws SQLException, MappingException {
        try (Statement stmt = connection.createStatement()) {


            StringBuilder builder = new StringBuilder();
            StringJoiner nameBuilder = new StringJoiner(",");
            StringJoiner valueBuilder = new StringJoiner(",");
            builder.append("insert into ");


            Annotation tableAnnotation = user.getClass().getAnnotation(Table.class);
            if (tableAnnotation != null) {
                builder.append(((Table) tableAnnotation).name());
            } else throw new MappingException("Не могу сохранить объект. Нет названия таблицы!");


            Field[] fields = ReflectionHelper.getFields(user);

            for (int i = 0, fieldsLength = fields.length; i < fieldsLength; i++) {
                Field field = fields[i];
                Annotation annotation = field.getAnnotation(Column.class);
                if (annotation != null) {
                    String columnName = ((Column) annotation).name();
                    Object value = ReflectionHelper.getFieldValue(user, field.getName());

                    nameBuilder.add(columnName);
                    String stringValue;
                    if (value instanceof String) {
                        stringValue = "'" + value + "'";
                    } else stringValue = value.toString();
                    valueBuilder.add(stringValue);


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

            System.out.println("Executing: " + builder.toString());
            int result = stmt.executeUpdate(builder.toString(), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            long id = rs.getLong(1);
            user.setId(id);
            System.out.println("Updated: " + result + ", ID=" + id);

        }
    }

    private <T extends DataSet> T load(String query, Class<T> clazz) throws MappingException, SQLException {

        System.out.println("Executing " + query);

        T result = execQuery(query, resultSet -> {
            resultSet.next();
            if (resultSet.getRow() == 0) return null;
            return process(resultSet, clazz);
        });

        return result;

    }


    public <T extends DataSet> T load(long id, Class<T> clazz) throws MappingException, SQLException {


        Annotation tableAnnotation = clazz.getAnnotation(Table.class);

        if (tableAnnotation != null) {

            String query = "select * from " + ((Table) tableAnnotation).name() + " where id=" + id;
            T result = load(query, clazz);

            result.setId(id);

            return result;

        } else {
            throw new MappingException("Не могу сохранить объект. Нет названия таблицы!");
        }
    }

    public <T extends DataSet> T loadByName(String name, Class<T> clazz) throws MappingException, SQLException {
        Annotation tableAnnotation = clazz.getAnnotation(Table.class);

        if (tableAnnotation != null) {

            String query = "select * from " + ((Table) tableAnnotation).name() + " where name='" + name + "'";
            T result = load(query, clazz);

            //result.setId(id);

            return result;

        } else {
            throw new MappingException("Не могу сохранить объект. Нет названия таблицы!");
        }
    }

    public <T extends DataSet> List<T> loadAll(Class<T> clazz) throws MappingException, SQLException {
        Annotation tableAnnotation = clazz.getAnnotation(Table.class);

        final List<T> result = new ArrayList<>();
        if (tableAnnotation != null) {
            String query = "select * from " + ((Table) tableAnnotation).name();
            execQuery(query, resultSet -> {
                while (resultSet.next()){
                    result.add(process(resultSet, clazz));
                }
                return null;
            });

        } else {
            throw new MappingException("Не могу сохранить объект. Нет названия таблицы!");
        }

        return result;
    }

    private <T extends DataSet> T process(ResultSet resultSet, Class<T> clazz) throws SQLException {
        //System.out.println("resultSet: "+resultSet.getRow());
        if (resultSet.getRow() == 0) return null;
        Field[] fields = ReflectionHelper.getFields(clazz);
        T obj = ReflectionHelper.instantiate(clazz);
        for (Field field : fields) {
            System.out.println("field " + field);
            Annotation fieldAnnotation = field.getAnnotation(Column.class);
            if (fieldAnnotation != null) {
                ReflectionHelper.setFieldValue(obj, field.getName(), resultSet.getObject(((Column) fieldAnnotation).name()));
            } else {
                Annotation idAnnotation = field.getAnnotation(Id.class);
                if (idAnnotation != null) {
                    Long id = resultSet.getLong("id");
                    System.out.println("id: " + id);
                    obj.setId(id);
                }
            }
        }
        return obj;
    }




}
