package ru.otus.jsonparser.parsers;


import ru.otus.jsonparser.Context;
import ru.otus.jsonparser.abstractClasses.Parser;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import java.util.Collection;

public class CollectionParser extends Parser {
    @Override
    public JsonValue parse(Object object, Context context) {
        if (object instanceof Collection){
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            Collection list = (Collection) object;
            list.forEach(value-> arrayBuilder.add(context.getParser().serialize(value)));
            return arrayBuilder.build();
        }
        return getNext().parse(object, context);
    }
}
