package com.booking.context;

import io.restassured.response.Response;

public class TestContext {
    private String authToken;
    private Response response;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
