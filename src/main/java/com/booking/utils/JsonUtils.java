package com.booking.utils;

import com.booking.pojo.BookingRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

public interface JsonUtils {

    ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Load a specific BookingRequest from a JSON file by testDataKey.
     * JSON file structure:
     * {
     *   "validBooking": { ... },
     *   "missingEmail": { ... }
     * }
     */
    static BookingRequest loadBookingData(String filePath, String testDataKey) {
        return unchecked(() -> {
            JsonNode root = MAPPER.readTree(new File(filePath));
            JsonNode node = requireNode(root.get(testDataKey), testDataKey);
            return MAPPER.treeToValue(node, BookingRequest.class);
        });
    }

    /** Ensure the JsonNode exists */
    static JsonNode requireNode(JsonNode node, String key) {
        return node;
    }

    /** Wrap IOException into unchecked */
    static <T> T unchecked(IOFunction<T> fn) {
        try {
            return fn.apply();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to process JSON file", e);
        }
    }

    @FunctionalInterface
    interface IOFunction<T> {
        T apply() throws IOException;
    }
}
