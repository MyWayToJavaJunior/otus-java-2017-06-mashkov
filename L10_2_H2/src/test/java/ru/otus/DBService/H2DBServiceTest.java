package ru.otus.DBService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.otus.interfaces.DBService;
import ru.otus.models.AddressDataSet;
import ru.otus.models.PhoneDataSet;
import ru.otus.models.UserDataSet;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class H2DBServiceTest {
    DBService dbService;

    UserDataSet expected;
    UserDataSet expected2;
    UserDataSet expected3;

    @BeforeEach
    void setup(){
        dbService = new H2DBService();

        expected = new UserDataSet("bob", 12, new AddressDataSet("24 street"));
        expected2 = new UserDataSet("bill", 13, new AddressDataSet("25 street"));
        expected3 = new UserDataSet("ben", 14, new AddressDataSet("26 street"));

        expected.addPhone(new PhoneDataSet("2-12-85-06"));
        expected.addPhone(new PhoneDataSet("123-345"));
        expected2.addPhone(new PhoneDataSet("11111111"));
        expected3.addPhone(new PhoneDataSet("33333333"));
    }

    @AfterEach
    void tearDown(){
        dbService.shutdown();
    }
    @org.junit.jupiter.api.Test
    void save() {
        dbService.save(expected);
        System.out.println(expected);
        assertEquals(1, expected.getId());
    }

    @org.junit.jupiter.api.Test
    void read() {

        dbService.save(expected);
        UserDataSet actual = dbService.read(expected.getId());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getId(), actual.getId());
    }

    @org.junit.jupiter.api.Test
    void readByName() {
        dbService.save(expected);
        UserDataSet actual = dbService.readByName(expected.getName());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getId(), actual.getId());
    }

    @org.junit.jupiter.api.Test
    void readAll() {
        dbService.save(expected);
        dbService.save(expected2);
        dbService.save(expected3);
        List<UserDataSet> list = dbService.readAll();
        assertEquals(3, list.size());
    }

}