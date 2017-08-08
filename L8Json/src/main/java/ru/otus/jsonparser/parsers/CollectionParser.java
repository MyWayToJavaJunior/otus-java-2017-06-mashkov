package ru.otus.jsonparser.parsers;


import ru.otus.jsonparser.Context;
import ru.otus.jsonparser.JsonParserException;
import ru.otus.jsonparser.abstractClasses.Parser;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import java.util.Collection;

public class CollectionParser extends Parser {
    @Override
    public JsonValue parse(Object object, Context context) throws JsonParserException {
        if (object instanceof Collection){
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            Collection list = (Collection) object;
            list.forEach(value -> {
                try {
                    arrayBuilder.add(context.getParser().serialize(value));
                } catch (JsonParserException e) {
                    e.printStackTrace();
                }
            });
            return arrayBuilder.build();
        }else {
            if (hasNext()) return getNext().parse(object, context);
        }
        throw new JsonParserException("Не определён тип!");
    }
}
