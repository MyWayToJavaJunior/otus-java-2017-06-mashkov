package ru.otus.jsonparser.parsers;

import ru.otus.jsonparser.Context;
import ru.otus.jsonparser.abstractClasses.Parser;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class NumberParser extends Parser {

    public JsonValue parse(Object object, Context context) {

        if (object instanceof Number){
            JsonObjectBuilder builder = Json.createObjectBuilder();
            if (Integer.class.equals(object.getClass())) {
                //Не очень красивое решение, но я не знаю как лучше
                builder.add("val", Integer.class.cast(object));
            } else if (Long.class.equals(object.getClass())) {
                builder.add("val", Long.class.cast(object));
            } else if (Double.class.equals(object.getClass())) {
                builder.add("val", Double.class.cast(object));
            } else if (Float.class.equals(object.getClass())) {
                builder.add("val", Float.class.cast(object));
            } else if (Character.class.equals(object.getClass())) {
                builder.add("val", Character.class.cast(object));
            } else if (Byte.class.equals(object.getClass())) {
                builder.add("val", Byte.class.cast(object));
            }

            return builder.build().get("val");
        } else return getNext().parse(object, context);


    }
}
