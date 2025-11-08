package com.booking.utils;

import com.booking.constants.CommonConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ValidationConfig {

    private static Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(CommonConstants.VALIDATION_PROPERTIES_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load validation.properties", e);
        }
    }

    public static String getValidationKey(String type) {
        return properties.getProperty(type.toLowerCase(), "");
    }
}
