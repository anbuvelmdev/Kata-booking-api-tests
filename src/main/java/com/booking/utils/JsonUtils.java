package com.booking.utils;

import com.booking.pojo.BookingRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface JsonUtils {

    ObjectMapper MAPPER = new ObjectMapper();

    static BookingRequest loadBookingData(String filePath) {
        try {
            return MAPPER.readValue(new File(filePath), BookingRequest.class);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load booking data: " + filePath, e);
        }
    }

    static String readJsonFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read JSON file: " + filePath, e);
        }
    }
}
