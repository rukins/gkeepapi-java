package io.github.rukins.gkeepapi.config;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.rukins.gkeepapi.annotation.Exclude;
import io.github.rukins.gkeepapi.config.typeadapter.*;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.MimeType;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.blobobject.Blob;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.Node;

import java.time.LocalDateTime;
import java.util.Locale;

public class GsonConfig {
    private static final ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return field.getAnnotation(Exclude.class) != null;
        }
        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    };

    public static Gson gson() {
        return new GsonBuilder()
                .setExclusionStrategies(exclusionStrategy)
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Locale.class, new LocaleTypeAdapter())
                .registerTypeAdapter(Node.class, new NodeTypeAdapter())
                .registerTypeAdapter(Blob.class, new BlobTypeAdapter())
                .registerTypeAdapter(MimeType.class, new MimeTypeEnumTypeAdapter())
                .create();
    }
}
