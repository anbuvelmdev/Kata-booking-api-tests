package com.booking.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for creating and managing application logs.
 *
 * Centralizes all log-related functionality to ensure consistent logging
 * format and behavior across API tests and utility layers.
 */
public class LogUtils {

    /**
     * Returns a logger instance for the specified class.
     *
     * @param clazz The class requesting the logger
     * @return Logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Logs API request details in a standardized format.
     *
     * @param log    Logger instance
     * @param method HTTP method (e.g., GET, POST, PUT, DELETE)
     * @param url    Target endpoint URL
     * @param body   Optional request body to log (can be null)
     */
    public static void logApiRequest(Logger log, String method, String url, Object body) {
        log.info("➡️  Sending {} request to {}", method, url);
        if (body != null) {
            log.debug("Request Body: {}", body);
        }
    }
}
