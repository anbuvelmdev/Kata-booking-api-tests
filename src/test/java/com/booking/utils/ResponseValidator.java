package com.booking.utils;

import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;

/**
 * Utility class for validating REST API responses.
 *
 * Provides reusable methods for verifying status codes and JSON content in responses.
 * Uses JUnit assertions for compatibility with Cucumber or standalone test runners.
 */
public class ResponseValidator {

    private static final Logger log = LogUtils.getLogger(ResponseValidator.class);

    /**
     * Validates that the API response status code matches the expected value.
     *
     * @param response           API response object
     * @param expectedStatusCode Expected HTTP status code
     */
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actual = response.getStatusCode();
        log.info("Validating status code: expected={}, actual={}", expectedStatusCode, actual);
        Assert.assertEquals("Unexpected status code!", expectedStatusCode, actual);
    }

    /**
     * Validates that the given JSON path key exists in the response.
     *
     * @param response API response object
     * @param jsonPath JSON path key (e.g., "data.id" or "error.message")
     */
    public static void validateJsonHasKey(Response response, String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        Assert.assertNotNull(
                "Expected key '" + jsonPath + "' not found or value is null in response: " + response.asString(),
                value);
    }

    /**
     * Validates that the JSON path value equals the expected value.
     *
     * @param response API response object
     * @param jsonPath JSON path key
     * @param expected Expected value for comparison
     */
    public static void validateJsonEquals(Response response, String jsonPath, Object expected) {
        Object actual = response.jsonPath().get(jsonPath);
        Assert.assertNotNull("Expected key '" + jsonPath + "' not found in response: " + response.asString(), actual);
        Assert.assertEquals("Mismatch for key '" + jsonPath + "'.", expected, actual);
    }


}
