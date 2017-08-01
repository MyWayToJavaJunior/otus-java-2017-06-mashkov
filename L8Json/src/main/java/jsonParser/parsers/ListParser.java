package jsonParser.parsers;

import jsonParser.JsonParser;
import jsonParser.interfaces.Parser;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.AbstractList;

public class ListParser extends Parser {
    @Override
    public void parse(Object object, String fieldName, JsonObjectBuilder builder) {
        if (object instanceof AbstractList){
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            JsonParser parser = new JsonParser();
            for (int i = 0; i < ((AbstractList) object).size(); i++) {
                //parser.parse(((AbstractList) object).get(i), null, arrayBuilder);
            }
        }
    }
}
