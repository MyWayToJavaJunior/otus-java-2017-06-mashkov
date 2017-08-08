package ru.otus.jsonparser.parsers;

import ru.otus.jsonparser.Context;
import ru.otus.jsonparser.JsonParserException;
import ru.otus.jsonparser.abstractClasses.Parser;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class StringParser extends Parser {
    public JsonValue parse(Object object, Context context) throws JsonParserException {
        if (object instanceof String){
            JsonObjectBuilder builder = Json.createObjectBuilder();
            return builder.add("val", object.toString()).build().get("val");
        } else {
            if (hasNext()) return getNext().parse(object, context);
        }
        throw new JsonParserException("Не определён тип!");
    }
}
