package ru.otus.jsonparser.parsers;

import ru.otus.jsonparser.Context;
import ru.otus.jsonparser.abstractClasses.Parser;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.util.AbstractMap;
import java.util.Map;

public class MapParser extends Parser {
    @Override
    public JsonValue parse(Object object, Context context) {
        if (object instanceof AbstractMap){
            JsonObjectBuilder builder = Json.createObjectBuilder();

            Map<Object, Object> map = (Map) object;
            for (Map.Entry e :
                    map.entrySet()) {
                builder.add(e.getKey().toString(), context.getParser().serialize(e.getValue()));
            }
            
            return builder.build();
        } else return getNext().parse(object, context);
    }
}
