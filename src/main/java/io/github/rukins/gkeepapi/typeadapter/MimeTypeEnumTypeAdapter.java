package io.github.rukins.gkeepapi.typeadapter;

import com.google.gson.*;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.MimeType;

import java.lang.reflect.Type;

public class MimeTypeEnumTypeAdapter implements JsonSerializer<MimeType>, JsonDeserializer<MimeType> {
    @Override
    public JsonElement serialize(MimeType mimeType, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(mimeType.getValue());
    }
    @Override
    public MimeType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return MimeType.getByValue(jsonElement.getAsString());
    }
}
