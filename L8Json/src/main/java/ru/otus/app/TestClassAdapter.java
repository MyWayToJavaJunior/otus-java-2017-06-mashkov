package ru.otus.app;

import ru.otus.jsonparser.Context;
import ru.otus.jsonparser.abstractClasses.CustomParser;

import javax.json.JsonObjectBuilder;

public class TestClassAdapter extends CustomParser {

    @Override
    public JsonObjectBuilder serialize(Object o, Context context) {
        JsonObjectBuilder objectBuilder = context.getBuilder();
        objectBuilder.add("myStr", ((InnerTestClass)o).getStr());
        objectBuilder.add("myInt", ((InnerTestClass)o).getInteger());

        return objectBuilder;
    }
}
