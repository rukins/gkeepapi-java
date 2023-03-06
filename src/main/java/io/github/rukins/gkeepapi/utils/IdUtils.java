package io.github.rukins.gkeepapi.utils;

import java.util.UUID;

public class IdUtils {
    public static String generateId() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        return uuid.substring(0, 14) + "." + uuid.substring(15);
    }
}
