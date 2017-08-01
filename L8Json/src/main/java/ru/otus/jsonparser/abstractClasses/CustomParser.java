package ru.otus.jsonparser.abstractClasses;


import ru.otus.jsonparser.Context;

import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public abstract class CustomParser extends Parser {
    private Class type;

    public abstract JsonObjectBuilder serialize(Object o, Context context);

    public void setType(Class type){
        this.type = type;
    }

    @Override
    public JsonValue parse(Object object, Context context) {
        if (object.getClass().equals(type)){
            return serialize(object, context).build();
        }
        return getNext().parse(object, context);
    }
}
