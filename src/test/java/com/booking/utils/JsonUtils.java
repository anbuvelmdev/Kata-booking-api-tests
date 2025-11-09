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
     * Generic: loads a named key from a JSON file and maps to the provided class.
     * Keeps cyclomatic complexity low: checks presence in helper and throws readable errors.
     */
    public static <T> T loadJson(String filePath, String testDataKey, Class<T> clazz) {
        return unchecked(() -> {
            JsonNode root = MAPPER.readTree(new File(filePath));
            JsonNode node = root.get(testDataKey);
            requireNode(node, testDataKey);
            return MAPPER.treeToValue(node, clazz);
        });
    }

    private static void requireNode(JsonNode node, String key) {
        if (node == null || node.isMissingNode() || node.isNull()) {
            throw new IllegalArgumentException("Test data key not found: " + key);
        }
    }

    private static <R> R unchecked(IOFunction<R> fn) {
        try {
            return fn.apply();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to process JSON file", e);
        }
    }

    @FunctionalInterface
    interface IOFunction<R> {
        R apply() throws IOException;
    }
}
