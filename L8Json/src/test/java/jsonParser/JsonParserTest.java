package jsonParser;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.testFramework.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
    JsonParser parser;
    Gson gson;
    TestObj obj;

    @BeforeEach
    void setup(){
        parser = new JsonParser();
        gson = new Gson();
        obj = new TestObj();
    }

    @Test
    void parse() {
        String expected = gson.toJson(obj);
        System.out.println(expected);
        String actual = parser.toJson(obj);
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    void reflect(){
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        Field[] fields = ReflectionHelper.getFields(obj);
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            String name = f.getName();
            builder.append('"').append(name).append("\":");
            Class type = f.getType();
            if (type.getName().equals("java.lang.String")){
                builder.append('"').append((String) ReflectionHelper.getFieldValue(obj, name)).append("\"");
            } else if (type.getName().equals("int")){
                builder.append(ReflectionHelper.getFieldValue(obj, name));
            }
            if (i!=fields.length-1) builder.append(",");

            //String value = (String) ReflectionHelper.getFieldValue(obj, name);

        }
        builder.append("}");
        System.out.println(builder);
        String expected = gson.toJson(obj);
        TestObj result = gson.fromJson(builder.toString(), TestObj.class);
        assertEquals(expected, builder.toString());
        assertEquals(obj, result);
    }

}