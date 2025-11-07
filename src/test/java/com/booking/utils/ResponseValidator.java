package com.booking.utils;

import io.restassured.response.Response;
import org.junit.Assert;

public class ResponseValidator {

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        Assert.assertNotNull("Response is null - API call might have failed", response);
        Assert.assertEquals(
                "Unexpected status code!",
                expectedStatusCode,
                response.statusCode()
        );
    }
}
