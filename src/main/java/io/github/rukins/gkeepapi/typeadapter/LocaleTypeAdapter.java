package io.github.rukins.gkeepapi.typeadapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Locale;

public class LocaleTypeAdapter implements JsonSerializer<Locale>, JsonDeserializer<Locale> {
    @Override
    public JsonElement serialize(Locale locale, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(
                locale.toString()
        );
    }

    @Override
    public Locale deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new Locale.Builder()
                .setLanguageTag(jsonElement.getAsString().replace("_", "-"))
                .build();
    }
}
