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
}
