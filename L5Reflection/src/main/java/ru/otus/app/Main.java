package ru.otus.app;

import ru.otus.testFramework.TestFramework;
import ru.otus.tests.SecondTestClass;
import ru.otus.tests.SimpleTest;

public class Main {
    public static void main(String[] args) {

        TestFramework.runTests("ru.otus.tests");
        //TestFramework.runTests(SimpleTest.class, SecondTestClass.class);
        //TestFramework.runTests(SimpleTest.class);
    }
}
