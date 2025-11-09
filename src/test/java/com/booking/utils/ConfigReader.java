package com.booking.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for loading configuration properties.
 *
 * Loads the properties file once at class initialization and
 * provides a static method to fetch configuration values by key.
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    // Static initializer loads the properties once at startup
    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /**
     * Retrieves the property value for the given key.
     *
     * @param key Property key to look up
     * @return Value associated with the key, or null if not found
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
