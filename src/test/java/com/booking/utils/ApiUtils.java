package com.booking.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * Utility class for making REST API calls using Rest-Assured.
 * Provides reusable methods for GET, POST, PUT, and DELETE requests.
 *
 * All methods create a RequestSpecification via RequestSpecFactory
 * and return the full Response for flexible validation.
 */
public final class ApiUtils {

    private ApiUtils() {
    }

    /**
     * Executes a POST request.
     *
     * @param baseUrl  Base URL of the API
     * @param endpoint API endpoint (relative path)
     * @param body     Request payload
     * @param token    Optional authentication token (e.g., session cookie)
     * @return         Response object from the API
     */
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

    /**
     * Executes a PUT request.
     *
     * @param baseUrl  Base URL of the API
     * @param endpoint API endpoint (relative path)
     * @param body     Request payload
     * @param token    Optional authentication token
     * @return         Response object from the API
     */
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

    /**
     * Executes a GET request.
     *
     * @param baseUrl  Base URL of the API
     * @param endpoint API endpoint (relative path)
     * @param token    Optional authentication token
     * @return         Response object from the API
     */
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

    /**
     * Executes a DELETE request.
     *
     * @param baseUrl  Base URL of the API
     * @param endpoint API endpoint (relative path)
     * @param token    Optional authentication token
     * @return         Response object from the API
     */
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
