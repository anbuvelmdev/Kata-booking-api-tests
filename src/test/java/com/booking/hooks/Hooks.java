package com.booking.hooks;

import com.booking.context.TestContext;
import com.booking.utils.ApiUtils;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import java.util.Map;

public class Hooks {
    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before("@requiresAuth")
    public void setupAuth() {
        Response res = ApiUtils.authLogin("/auth/login", Map.of("username", "admin", "password", "password"));
        context.setAuthToken(res.jsonPath().getString("token"));
    }
}
