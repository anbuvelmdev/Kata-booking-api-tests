package com.booking.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    /**
     * Generic: loads a named testDataKey from a JSON file and maps to the provided class.
     * checks presence in helper and throws readable errors.
     */
    public static <T> T loadJson(String filePath, String testDataKey, Class<T> bookingRequestClass) {
        return unchecked(() -> {
            JsonNode root = MAPPER.readTree(new File(filePath));
            JsonNode node = root.get(testDataKey);
            return MAPPER.treeToValue(node, bookingRequestClass);
        });
    }

    private static <R> R unchecked(IOFunction<R> fn) {
        try {
            return fn.apply();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to process Test data JSON file", e);
        }
    }

    @FunctionalInterface
    interface IOFunction<R> {
        R apply() throws IOException;
    }
}
