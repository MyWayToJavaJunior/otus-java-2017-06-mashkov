package ru.otus.jsonparser;

import ru.otus.jsonparser.abstractClasses.CustomParser;
import ru.otus.jsonparser.abstractClasses.Parser;

import java.util.ArrayList;

public class SimpleJsonParserBuilder {
    private SimpleJsonParser jsonParser;
    private ArrayList<Parser> parsers;

    public SimpleJsonParserBuilder(){
        parsers = new ArrayList<>();
    }

    public SimpleJsonParserBuilder setTypeAdapter(CustomParser parser, Class type){
        parser.setType(type);
        parsers.add(parser);
        return this;
    }

    public SimpleJsonParser build() {
        jsonParser = new SimpleJsonParser(parsers);
        return jsonParser;
    }
}
