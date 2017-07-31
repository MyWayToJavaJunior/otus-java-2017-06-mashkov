package jsonParser.parsers;

import jsonParser.interfaces.Parser;

import javax.json.JsonObjectBuilder;

public class StringParser extends Parser {

    @Override
    public void parse(Object object, String fieldName, JsonObjectBuilder builder) {
        System.out.println("String: "+object+" "+fieldName);

        if (object instanceof String){
            builder.add(fieldName, String.class.cast(object));
        } else if (hasNext()) getNext().parse(object, fieldName, builder);
    }
}
