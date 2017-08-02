package jsonParser;


import com.google.gson.*;
import ru.otus.app.InnerTestClass;

import java.lang.reflect.Type;

public class InnerTypeAdapter implements JsonDeserializer<InnerTestClass>, JsonSerializer<InnerTestClass> {
    @Override
    public InnerTestClass deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(InnerTestClass innerTestClass, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("myStr", innerTestClass.getStr());
        result.addProperty("myInt", innerTestClass.getInteger());
        return result;
    }
}
