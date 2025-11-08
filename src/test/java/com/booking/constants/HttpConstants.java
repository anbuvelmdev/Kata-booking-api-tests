package com.booking.constants;

import io.restassured.http.ContentType;

public final class HttpConstants {

    private HttpConstants() {}

    // Common headers
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";

    // Prefer RestAssured built-in ContentType
    public static final ContentType JSON = ContentType.JSON;
}