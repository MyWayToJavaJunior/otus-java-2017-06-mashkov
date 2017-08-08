package ru.otus.app;


import ru.otus.jsonparser.JsonParserException;
import ru.otus.jsonparser.SimpleJsonParser;
import ru.otus.jsonparser.SimpleJsonParserBuilder;



public class Main {
    public static void main(String[] args) {

        SimpleJsonParser parser = new SimpleJsonParserBuilder()
                .setTypeAdapter(new TestClassAdapter(), InnerTestClass.class)
                .build();
        try {
            System.out.println(parser.toJson(new TestClass()));
        } catch (JsonParserException e) {
            e.printStackTrace();
        }
    }


}
