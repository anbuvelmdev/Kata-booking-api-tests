package com.booking.utils;

import com.booking.config.ConfigReader;
import com.booking.constants.ConfigKeys;
import com.booking.constants.HttpConstants;
import com.booking.pojo.BookingRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    private static final Logger log = LogUtils.getLogger(ApiUtils.class);
    private static final String BASE_URL = ConfigReader.get(ConfigKeys.BASE_URL);
    public ApiUtils() {
        RestAssured.baseURI = BASE_URL;
    }

    public static Response authLogin(String authEndpoint, Map<String, String> credentials) {
        return given()
                .log().all()
                .contentType(HttpConstants.JSON)
                .body(credentials)
                .post(BASE_URL + authEndpoint);
    }


    public Response postBooking(String endpoint, BookingRequest request, String token) {
        return given()
                .header(HttpConstants.AUTHORIZATION, "Bearer " + token)
                .header(HttpConstants.CONTENT_TYPE, HttpConstants.JSON)
                .body(request)
                .log().all()
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response postBookingWithoutAuth(String endpoint, BookingRequest request) {
        LogUtils.logApiRequest(log, "POST", endpoint, request);
        return given()
                .contentType(HttpConstants.JSON)
                .body(request)
                .log().all()
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getBooking(String endpoint, String token) {
        LogUtils.logApiRequest(log, "GET", endpoint, null);
        return  given()
                .header(HttpConstants.AUTHORIZATION, "Bearer " + token)
                .header(HttpConstants.CONTENT_TYPE, HttpConstants.JSON)
                .log().all()
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getBookingWithoutAuth(String endpoint) {
        LogUtils.logApiRequest(log, "GET", endpoint, null);
        return  given()
                .contentType(HttpConstants.JSON)
                .log().all()
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response putRequest(String endpoint, Object body, String token) {
        LogUtils.logApiRequest(log, "PUT", endpoint, body);
        return given()
                .log().all()
                .header(HttpConstants.AUTHORIZATION, "Bearer " + token)
                .header(HttpConstants.CONTENT_TYPE, HttpConstants.JSON)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response deleteRequest(String endpoint, String token) {
        LogUtils.logApiRequest(log, "DELETE", endpoint, null);
        return given()
                .header(HttpConstants.AUTHORIZATION, "Bearer " + token)
                .header(HttpConstants.CONTENT_TYPE, HttpConstants.JSON)
                .delete(endpoint);
    }
}
