package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.testClass.TestClass;

public class SimpleTest {
    TestClass testClass;

    @Before
    public void setup(){
        System.out.println("it is Before method!");
        testClass = new TestClass();
    }

    @Test
    public void firstTest(){
        testClass.setString("it is first string!");
        System.out.println("it is first test! Message from test class: "+testClass.getString());

    }

    @Test
    public void secondTest(){
        System.out.println("it is second test! Message from test class: "+testClass.getString());
    }

    @After
    public void teardown(){
        System.out.println("it is After method");
    }
}
