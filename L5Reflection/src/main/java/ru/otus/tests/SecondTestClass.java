package ru.otus.tests;

import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class SecondTestClass {
    @Before
    public void setup(){
        System.out.println("it is Before method from "+SecondTestClass.class.getName());
    }

    @Test
    public void simpleTest(){
        System.out.println("it is Test method from "+SecondTestClass.class.getName());
    }
}
