package jsonParser;

import ru.otus.testFramework.ReflectionHelper;

import javax.json.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class JsonParser {

    public String toJson(Object object){
        String result = null;

        JsonObject jsonObject = parse(object);

        return jsonObject.toString();
    }

    private JsonObject parse(Object object){
        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        if (object instanceof AbstractList){
            //parse as list
        } else if (object instanceof AbstractMap) {
            HashMap map = (HashMap)object;
            for (Object entry: map.entrySet()) {
                Map.Entry<Object, Object> e = (Map.Entry<Object,Object>)entry;
                objectBuilder.add((String) e.getKey(), (String) e.getValue());
            }
        } else {
            final Field[] fields = ReflectionHelper.getFields(object);

            Arrays.asList(fields).forEach(f->{
                String name = f.getName();
                Class type = f.getType();
                Object value = ReflectionHelper.getFieldValue(object, name);

                addValue(value, name, objectBuilder);

            });

        }


        return objectBuilder.build();
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
