package com.booking.utils;

import com.booking.constants.HttpConstants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

/**
 * Factory utility for creating reusable {@link RequestSpecification} instances.
 *
 * Keeps Rest-Assured request setup modular and consistent across all API calls.
 * Provides optional token-based authentication handling.
 */
public final class RequestSpecFactory {

    private RequestSpecFactory() {
    }

    /**
     * Creates a reusable {@link RequestSpecification} with base URL,
     * content type, logging, and optional authentication header.
     *
     * @param baseUrl Base URI of the API (e.g., https://api.example.com)
     * @param token   Optional auth token (null or blank means no header added)
     * @return Configured {@link RequestSpecification}
     */
    public static RequestSpecification create(String baseUrl, String token) {
        RequestSpecBuilder builder = initBuilder(baseUrl);
        addTokenHeader(builder, token);
        return builder.build();
    }

    /**
     * Initializes base request configuration.
     * Handles base URI, content type, and logging detail.
     */
    private static RequestSpecBuilder initBuilder(String baseUrl) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(HttpConstants.APPLICATION_JSON)
                .log(LogDetail.ALL);
    }

    /**
     * Adds authentication header if token is provided.
     * Uses Optional to avoid nested conditionals.
     */
    private static void addTokenHeader(RequestSpecBuilder builder, String token) {
        java.util.Optional
                .ofNullable(token)
                .filter(t -> !t.isBlank())
                .ifPresent(t -> builder.addHeader(HttpConstants.COOKIE, HttpConstants.COOKIE_TOKEN + t));
    }
}
