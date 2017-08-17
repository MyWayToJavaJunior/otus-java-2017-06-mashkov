package ru.otus.DBService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.otus.interfaces.DBService;
import ru.otus.models.UserDataSet;

import static org.junit.jupiter.api.Assertions.*;

class H2DBServiceTest {
    DBService dbService;

    @BeforeEach
    void setup(){
        dbService = new H2DBService();
    }

    @AfterEach
    void tearDown(){
        dbService.shutdown();
    }
    @org.junit.jupiter.api.Test
    void save() {
        UserDataSet userDataSet = new UserDataSet("bob", 12);
        UserDataSet userDataSet2 = new UserDataSet("bill", 12);
        dbService.save(userDataSet);
        dbService.save(userDataSet2);
        System.out.println(userDataSet);
        System.out.println(userDataSet2);
        assertEquals(1, userDataSet.getId());
        assertEquals(2, userDataSet2.getId());
    }

    @org.junit.jupiter.api.Test
    void read() {
        UserDataSet expected = new UserDataSet("bob", 12);
        dbService.save(expected);
        System.out.println(expected);
        UserDataSet actual = dbService.read(expected.getId());
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void readByName() {
    }

    @org.junit.jupiter.api.Test
    void readAll() {
    }

}