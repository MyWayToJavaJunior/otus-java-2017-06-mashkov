package ru.otus.executor;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.otus.annotations.Test;
import ru.otus.dbhelper.DbHelper;
import ru.otus.exceptions.MappingException;
import ru.otus.models.DataSet;
import ru.otus.models.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorTest {
    static Connection connection;
    static Executor executor;

    @BeforeAll
    static void setup() {
        connection = DbHelper.getConnection();
        executor = new Executor(connection);

        try {
            executor.execUpdate("create table if not exists users (id  bigserial not null, name varchar(256), age int not null, primary key (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void save() {
        UserDataSet user = new UserDataSet("john", 24);
        try {
            executor.save(user);

            String query = "select * from users where id=" + user.getId();

            executor.execQuery(query, rs -> {
                rs.next();
                assertEquals(user.getAge(), rs.getInt("age"));
                assertEquals(user.getName(), rs.getString("name"));
                return null;
            });
        } catch (SQLException | MappingException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void load() {
        UserDataSet expected = new UserDataSet("smith", 30);
        try {
            executor.save(expected);
            UserDataSet actual = executor.load(expected.getId(), UserDataSet.class);
            System.out.println("expected " + expected);
            System.out.println("actual " + actual);
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getId(), actual.getId());
        } catch (MappingException | SQLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void exception(){
        Throwable throwable = assertThrows(MappingException.class, ()-> executor.save(new ExceptionDataSet()));
        assertEquals("Не могу сохранить объект. Нет названия таблицы!", throwable.getMessage());
    }

    @AfterAll
    static void tearDown() {
        try {
            executor.execUpdate("drop table users");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static class ExceptionDataSet extends DataSet{

    }

}