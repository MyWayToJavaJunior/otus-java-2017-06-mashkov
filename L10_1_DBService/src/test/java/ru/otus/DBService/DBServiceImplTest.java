package ru.otus.DBService;

import org.junit.jupiter.api.*;
import ru.otus.annotations.After;
import ru.otus.dbhelper.DbHelper;
import ru.otus.exceptions.MappingException;
import ru.otus.executor.Executor;
import ru.otus.interfaces.DBService;
import ru.otus.models.UserDataSet;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DBServiceImplTest {
    static DBService dbService;
    static Executor executor;

    @BeforeAll
    static void setUp() {
            executor = new Executor(DbHelper.getConnection());
            dbService = new DBServiceImpl();
            //DbHelper.getConnection().setAutoCommit(false);


    }

    @BeforeEach
    void before(){
        try {
            executor.execUpdate("create table if not exists users2 (id  bigserial not null, age int, name varchar(256), pass varchar(256),status varchar(256), primary key (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @AfterAll
    static void tearDown() {

        DbHelper.close();
    }

    @AfterEach
    void after() throws SQLException {
        executor.execUpdate("drop table users2");
    }

    @Test
    void save() {
        System.out.println("Save Test");
        UserDataSet user = new UserDataSet("john", 25);
        dbService.save(user);
        UserDataSet actualUser = null;
        try {
            actualUser = executor.load(user.getId(), UserDataSet.class);
        } catch (SQLException | MappingException e) {
            e.printStackTrace();
        }
        System.out.println("expected: "+user);
        System.out.println("actual: "+actualUser);
        assertEquals(user, actualUser);

    }

    @Test
    void read() {
        System.out.println("Read Test");
        UserDataSet expectedUser = new UserDataSet("john", 34);
        try {
            executor.save(expectedUser);
        } catch (SQLException | MappingException e) {
            e.printStackTrace();
        }
        UserDataSet actualUser = dbService.read(expectedUser.getId());
        System.out.println("expected: "+expectedUser);
        System.out.println("actual: "+actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void readByName() {
        System.out.println("Read by Name Test");
        UserDataSet expectedUser = new UserDataSet("john", 21);
        try {
            executor.save(expectedUser);
        } catch (SQLException | MappingException e) {
            e.printStackTrace();
        }
        UserDataSet actualUser = dbService.readByName(expectedUser.getName());
        System.out.println("expected: "+expectedUser);
        System.out.println("actual: "+actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void readAll() {
        System.out.println("Read All Test");
        UserDataSet user1 = new UserDataSet("john", 21);
        UserDataSet user2 = new UserDataSet("bill", 23);
        UserDataSet user3 = new UserDataSet("freddy", 24);

        try {
            executor.save(user1);
            executor.save(user2);
            executor.save(user3);
        } catch (SQLException | MappingException e) {
            e.printStackTrace();
        }

        List<UserDataSet> users = dbService.readAll();

        assertEquals(3, users.size());
    }

    @Test
    void statusTest(){
        System.out.println(dbService.getLocalStatus());
    }

}