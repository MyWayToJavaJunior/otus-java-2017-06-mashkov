package jsonParser.parsers;

import jsonParser.JsonParser;
import jsonParser.interfaces.Parser;
import ru.otus.testFramework.ReflectionHelper;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ObjectParser extends Parser {
    @Override
    public void parse(Object object, String fieldName, JsonObjectBuilder builder) {
        JsonObjectBuilder newObjectBuilder = Json.createObjectBuilder();
        JsonParser parser = new JsonParser();

        Field[] fields = ReflectionHelper.getFields(object);

        for (Field field : fields) {
            parser.parse(ReflectionHelper.getFieldValue(object, field.getName()), field.getName(), newObjectBuilder);
        }

        builder.add(fieldName, newObjectBuilder);
    }
}
