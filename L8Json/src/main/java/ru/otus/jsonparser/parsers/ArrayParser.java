package ru.otus.jsonparser.parsers;

import ru.otus.jsonparser.Context;
import ru.otus.jsonparser.JsonParserException;
import ru.otus.jsonparser.abstractClasses.Parser;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayParser extends Parser {
    @Override
    public JsonValue parse(Object object, Context context) throws JsonParserException {

        if (object.getClass().isArray()){
            JsonArrayBuilder builder = Json.createArrayBuilder();
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++) {
                Object value = Array.get(object, i);
                builder.add(context.getParser().serialize(value));
            }
            return builder.build();
        }else {
            if (hasNext()) return getNext().parse(object, context);
        }
        throw new JsonParserException("Не определён тип!");
    }
}
