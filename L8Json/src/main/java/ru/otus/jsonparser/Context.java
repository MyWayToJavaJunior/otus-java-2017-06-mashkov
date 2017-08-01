package ru.otus.jsonparser;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Context {
    private SimpleJsonParser parser;

    public JsonObjectBuilder getBuilder() {
        return Json.createObjectBuilder();
    }

    public SimpleJsonParser getParser() {
        return parser;
    }

    public void setParser(SimpleJsonParser parser) {
        this.parser = parser;
    }
}
