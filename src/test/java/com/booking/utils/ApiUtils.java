package com.booking.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class ApiUtils {

    private ApiUtils() {
    }

    public static Response post(String baseUrl, String endpoint, Object body, String token) {
        RequestSpecification spec = RequestSpecFactory.create(baseUrl, token);
        return given()
                .spec(spec)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response put(String baseUrl, String endpoint, Object body, String token) {
        RequestSpecification spec = RequestSpecFactory.create(baseUrl, token);
        return given()
                .spec(spec)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response get(String baseUrl, String endpoint, String token) {
        RequestSpecification spec = RequestSpecFactory.create(baseUrl, token);
        return given()
                .spec(spec)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response delete(String baseUrl, String endpoint, String token) {
        RequestSpecification spec = RequestSpecFactory.create(baseUrl, token);
        return given()
                .spec(spec)
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
