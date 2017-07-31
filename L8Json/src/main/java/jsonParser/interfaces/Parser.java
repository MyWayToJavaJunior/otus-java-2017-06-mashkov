package jsonParser.interfaces;

import javax.json.JsonObjectBuilder;

public abstract class Parser {
    private Parser next;

    public abstract void parse(Object object, String fieldName, JsonObjectBuilder builder);

    public void setNext(Parser parser) {
        this.next = parser;
    }
    public Parser getNext() {
        return next;
    }
    public boolean hasNext() {
        return next!=null;
    }
}