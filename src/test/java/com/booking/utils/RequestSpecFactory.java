package com.booking.utils;

import com.booking.constants.HttpConstants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private RequestSpecFactory() {
    }

    public static RequestSpecification create(String baseUrl, String token) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(HttpConstants.APPLICATION_JSON)
                .log(LogDetail.ALL);
        if (token != null && !token.isBlank()) {
            builder.addHeader(HttpConstants.COOKIE, HttpConstants.COOKIE_TOKEN + token);
        }
        return builder.build();
    }
}
