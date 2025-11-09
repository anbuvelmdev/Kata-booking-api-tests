package com.booking.constants;

import io.restassured.http.ContentType;

public final class HttpConstants {

    // Common headers
    public static final String COOKIE = "Cookie";
    public static final String COOKIE_TOKEN = "token=";
    public static final String APPLICATION_JSON = "application/json";
    // RestAssured built-in ContentType
    public static final ContentType JSON = ContentType.JSON;

    private HttpConstants() {
    }
}