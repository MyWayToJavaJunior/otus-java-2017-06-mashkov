package jsonParser.parsers;

import jsonParser.interfaces.Parser;

import javax.json.JsonObjectBuilder;

public class NumberParser extends Parser {
    private Parser next;

    public NumberParser(){
        next = null;
    }

    @Override
    public void parse(Object object, String fieldName, JsonObjectBuilder builder) {
        System.out.println("Number: "+object+" "+fieldName);
        if (object instanceof Number){
            if (Integer.class.equals(object.getClass())) {
                builder.add(fieldName, Integer.class.cast(object));
            } else if (Long.class.equals(object.getClass())) {
                builder.add(fieldName, Long.class.cast(object));
            } else if (Double.class.equals(object.getClass())) {
                builder.add(fieldName, Double.class.cast(object));
            } else if (Float.class.equals(object.getClass())) {
                builder.add(fieldName, Float.class.cast(object));
            } else if (Character.class.equals(object.getClass())) {
                builder.add(fieldName, Character.class.cast(object));
            } else if (Byte.class.equals(object.getClass())) {
                builder.add(fieldName, Byte.class.cast(object));
            }
        } else if (hasNext()) getNext().parse(object, fieldName, builder);
    }

}
