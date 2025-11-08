package com.booking.utils;

import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;

public class ResponseValidator {

    private static final Logger log = LogUtils.getLogger(ResponseValidator.class);

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actual = response.getStatusCode();
        log.info("Validating status code: expected={}, actual={}", expectedStatusCode, actual);
        Assert.assertEquals(
                "Unexpected status code!",
                expectedStatusCode,
                actual
        );
    }

    public static void validateJsonHasKey(Response response, String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        Assert.assertNotNull(
                "Expected key '" + jsonPath + "' not found or value is null in response: " + response.asString(),
                value
        );
    }

    public static void validateJsonEquals(Response response, String jsonPath, Object expected) {
        Object actual = response.jsonPath().get(jsonPath);
        Assert.assertNotNull(
                "Expected key '" + jsonPath + "' not found in response: " + response.asString(),
                actual
        );
        Assert.assertEquals(
                "Mismatch for key '" + jsonPath + "'.",
                expected,
                actual
        );
    }


}
