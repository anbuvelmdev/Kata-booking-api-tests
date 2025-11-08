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
            log.debug("Request Body: {}", body);
        }
    }
}
