package io.github.rukins.gkeepapi.typeadapter;

import com.google.gson.*;
import io.github.rukins.gkeepapi.model.Timestamps;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(
                localDateTime.format(
                        DateTimeFormatter.ofPattern(Timestamps.DEFAULT_DATETIME_FORMAT_PATTERN)
                )
        );
    }

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return LocalDateTime.parse(
                jsonElement.getAsString(),
                DateTimeFormatter.ofPattern(Timestamps.DEFAULT_DATETIME_FORMAT_PATTERN)
        );
    }
}