package com.booking.utils;

import com.booking.constants.HttpConstants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private RequestSpecFactory() {
    }

    public static RequestSpecification create(String baseUrl, String token) {
        RequestSpecBuilder builder = initBuilder(baseUrl);
        addTokenHeader(builder, token);
        return builder.build();
    }

    private static RequestSpecBuilder initBuilder(String baseUrl) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(HttpConstants.APPLICATION_JSON)
                .log(LogDetail.ALL);
    }

    private static void addTokenHeader(RequestSpecBuilder builder, String token) {
        java.util.Optional
                .ofNullable(token)
                .filter(t -> !t.isBlank())
                .ifPresent(t -> builder.addHeader(HttpConstants.COOKIE, HttpConstants.COOKIE_TOKEN + t));
    }
}
