package ru.otus.app;


import ru.otus.jsonparser.SimpleJsonParser;
import ru.otus.jsonparser.SimpleJsonParserBuilder;


public class Main {
    public static void main(String[] args) {

        SimpleJsonParser parser = new SimpleJsonParserBuilder()
                .setTypeAdapter(new TestClassAdapter(), InnerTestClass.class)
                .build();
        System.out.println(parser.toJson(new TestClass()));
    }


}
