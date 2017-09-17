package ru.otus.executor;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.otus.dbhelper.DbHelper;
import ru.otus.exceptions.MappingException;
import ru.otus.models.DataSet;
import ru.otus.models.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorTest {
    static Connection connection;
    static Executor executor;

    @BeforeAll
    static void setup() {
        connection = DbHelper.getConnection();
        executor = new Executor(connection);

        try {
            executor.execUpdate("create table if not exists users2 (id  bigserial not null, age int, name varchar(256), pass varchar(256),status varchar(256), primary key (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void save() {
        UserDataSet user = new UserDataSet("john", 24);
        try {
            executor.save(user);

            String query = "select * from users2 where id=" + user.getId();

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
    void loadByName(){
        UserDataSet expected = new UserDataSet("smith", 30);
        try {
            executor.save(expected);
            UserDataSet actual = executor.loadByName(expected.getName(), UserDataSet.class);
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
    void loadAllTest(){
        UserDataSet user1 = new UserDataSet("barack obama", 50);
        UserDataSet user2 = new UserDataSet("george bush", 70);

        try {
            executor.save(user1);
            executor.save(user2);

            List<UserDataSet> users = executor.loadAll(UserDataSet.class);

            assertEquals(2, users.size());
        } catch (SQLException | MappingException e) {
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
            executor.execUpdate("drop table users2");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static class ExceptionDataSet extends DataSet{

    }

}