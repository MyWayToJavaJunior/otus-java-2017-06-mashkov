package ru.otus.tests;

import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class SecondTestClass {
    @Before
    public void setup(){
        System.out.println("it is Before method from second class");
    }

    @Test
    public void simpleTest(){
        System.out.printf("it is Test method from second class");
    }
}
