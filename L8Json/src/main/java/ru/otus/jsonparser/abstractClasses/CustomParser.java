package ru.otus.jsonparser.abstractClasses;


import ru.otus.jsonparser.Context;
import ru.otus.jsonparser.JsonParserException;

import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public abstract class CustomParser extends Parser {
    private Class type;

    public abstract JsonObjectBuilder serialize(Object o, Context context);

    public void setType(Class type){
        this.type = type;
    }

    @Override
    public JsonValue parse(Object object, Context context) throws JsonParserException {
        if (object.getClass().equals(type)){
            return serialize(object, context).build();
        }else {
            if (hasNext()) return getNext().parse(object, context);
        }
        throw new JsonParserException("Не определён тип!");
    }
}
