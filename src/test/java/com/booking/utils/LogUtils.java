package com.booking.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static void logApiRequest(Logger log, String method, String url, Object body) {
        log.info("➡️  Sending {} request to {}", method, url);
        if (body != null) {
            log.debug("📦 Request Body: {}", body);
        }
    }

    public static void logApiResponse(Logger log, int statusCode, String body) {
        log.info("⬅️  Received Response: HTTP {}", statusCode);
        log.debug("🧾 Response Body: {}", body);
    }

    public static void logError(Logger log, String message, Exception e) {
        log.error("❌ {} - {}", message, e.getMessage());
        log.debug("Stack trace: ", e);
    }
}
