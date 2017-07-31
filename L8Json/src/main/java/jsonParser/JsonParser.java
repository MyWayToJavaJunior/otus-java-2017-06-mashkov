package jsonParser;

import com.google.gson.JsonPrimitive;
import jsonParser.interfaces.Parser;
import jsonParser.parsers.NumberParser;
import jsonParser.parsers.StringParser;
import ru.otus.testFramework.ReflectionHelper;

import javax.json.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class JsonParser {

    private Parser parser;

    public JsonParser(){
        parser = new NumberParser();
        parser.setNext(new StringParser());
    }

    public String toJson(Object object){
        /*String result = null;

        JsonValue jsonObject = parse(object);*/

        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        Field[] fields = ReflectionHelper.getFields(object);

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            parser.parse(ReflectionHelper.getFieldValue(object, field.getName()), field.getName(), objectBuilder);
        }


        return objectBuilder.build().toString();
    }



    private JsonValue parse(Object object){


        if (object instanceof AbstractList){
            return parseList(object);
        } else if (object instanceof AbstractMap) {
            return parseMap(object);
        } else if (object instanceof Number) {
            return parseNumber(object);
        } else if (object instanceof String){
            return parseString(object);
        }
        else {
            return parseObj(object);

        }


        //return objectBuilder.build();
    }

    private JsonValue parseString(Object object) {


        return null;
    }

    private JsonValue parseNumber(Object object) {
        /*if (Integer.class.equals(object.getClass())) {
            builder.add(name, Integer.class.cast(object));
            return;
        }
        if (Long.class.equals(value.getClass())) {
            builder.add(name, Long.class.cast(value));
            return;
        }
        if (Double.class.equals(value.getClass())) {
            builder.add(name, Double.class.cast(value));
            return;
        }
        if (Float.class.equals(value.getClass())) {
            builder.add(name, Float.class.cast(value));
            return;
        }
        if (Character.class.equals(value.getClass())) {
            builder.add(name, Character.class.cast(value));
            return;
        }
        if (Byte.class.equals(value.getClass())) {
            builder.add(name, Byte.class.cast(value));
            return;
        }*/
        return null;

    }

    private JsonValue parseMap(Object object){

        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        HashMap map = (HashMap)object;
        for (Object entry: map.entrySet()) {
            Map.Entry<Object, Object> e = (Map.Entry<Object,Object>)entry;
            objectBuilder.add((String) e.getKey(), (String) e.getValue());
        }

        return objectBuilder.build();

    }

    private JsonValue parseList(Object object){

        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        List<Object> list = (List<Object>)object;
        list.forEach(v->{
            arrayBuilder.add(parse(v));
        });
        return arrayBuilder.build();

    }

    private JsonValue parseObj(Object object){
        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        final Field[] fields = ReflectionHelper.getFields(object);

        Arrays.asList(fields).forEach(f->{
            String name = f.getName();
            Class type = f.getType();
            Object value = ReflectionHelper.getFieldValue(object, name);

            addValue(value, name, objectBuilder);

        });
        return null;
    }

    private static void addValue(Object value, String name, JsonObjectBuilder builder){
        if (value instanceof Number){

            if (Integer.class.equals(value.getClass())) {
                builder.add(name, Integer.class.cast(value));
                return;
            }
            if (Long.class.equals(value.getClass())) {
                builder.add(name, Long.class.cast(value));
                return;
            }
            if (Double.class.equals(value.getClass())) {
                builder.add(name, Double.class.cast(value));
                return;
            }
            if (Float.class.equals(value.getClass())) {
                builder.add(name, Float.class.cast(value));
                return;
            }
            if (Character.class.equals(value.getClass())) {
                builder.add(name, Character.class.cast(value));
                return;
            }
            if (Byte.class.equals(value.getClass())) {
                builder.add(name, Byte.class.cast(value));
                return;
            }


        } else if (value instanceof String){
            builder.add(name, String.class.cast(value));
            return;
        } else if (AbstractList.class.equals(value.getClass())){
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        }



    }


}
