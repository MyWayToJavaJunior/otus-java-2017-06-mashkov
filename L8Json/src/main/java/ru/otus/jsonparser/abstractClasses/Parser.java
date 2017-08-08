package ru.otus.jsonparser.abstractClasses;


import ru.otus.jsonparser.Context;
import ru.otus.jsonparser.JsonParserException;

import javax.json.JsonValue;

public abstract class Parser {
    private Parser next = null;

    public abstract JsonValue parse(Object object, Context context) throws JsonParserException;

    protected Parser getNext() {
        return next;
    }

    public Parser setNext(Parser next){
        this.next = next;
        return next;
    }

    public boolean hasNext(){
        return next!=null;
    }
}
