package com.booking.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Utility class for reading JSON test data files.
 *
 * Supports fetching a specific key (testDataKey) from a JSON file
 * and mapping it to a target class type.
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    /**
     * Loads a specific testDataKey from the JSON file and maps it to the given class type.
     *
     * @param filePath                  Path to JSON file (e.g., src/test/resources/testdata.json)
     * @param testDataKey               Key to extract from JSON
     * @param bookingRequestClass       Class to map the JSON data into
     * @param <T>                       Generic type parameter for targetClass
     * @return                          Deserialized Java object of type T
     * @throws UncheckedIOException     if JSON file cannot be read
     */
    public static <T> T loadJson(String filePath, String testDataKey, Class<T> bookingRequestClass) {
        return unchecked(() -> {
            JsonNode root = MAPPER.readTree(new File(filePath));
            JsonNode node = root.get(testDataKey);
            return MAPPER.treeToValue(node, bookingRequestClass);
        });
    }

    /**
     * Wraps an IO operation, rethrowing checked exceptions as UncheckedIOException.
     * Keeps call sites clean and reduces code complexity.
     */
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
