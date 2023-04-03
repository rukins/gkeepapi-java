package io.github.rukins.gkeepapi.config.typeadapter;

import com.google.gson.*;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.BlobType;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.blobobject.Blob;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.blobobject.DrawingBlob;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.blobobject.ImageBlob;

import java.lang.reflect.Type;

public class BlobTypeAdapter implements JsonSerializer<Blob>, JsonDeserializer<Blob> {
    @Override
    public JsonElement serialize(Blob blob, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(blob, blob.getClass());
    }

    @Override
    public Blob deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return switch (BlobType.valueOf(jsonElement.getAsJsonObject().get("type").getAsString())) {
            case IMAGE -> jsonDeserializationContext.deserialize(jsonElement, ImageBlob.class);
            case DRAWING -> jsonDeserializationContext.deserialize(jsonElement, DrawingBlob.class);
        };
    }
}
