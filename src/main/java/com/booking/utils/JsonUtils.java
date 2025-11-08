package com.booking.utils;

import com.booking.pojo.BookingRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

public interface JsonUtils {
    ObjectMapper MAPPER = new ObjectMapper();
    static BookingRequest loadBookingData(String filePath) {
        return unchecked(() -> MAPPER.readValue(new File(filePath), BookingRequest.class));
    }

    // Helper for unchecked exceptions
    static <T> T unchecked(IOFunction<T> fn) {
        try {
            return fn.apply();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @FunctionalInterface
    interface IOFunction<T> {
        T apply() throws IOException;
    }

}
