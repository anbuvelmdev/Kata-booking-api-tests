package com.booking.hooks;

import com.booking.context.TestContext;
import com.booking.utils.ApiUtils;
import com.booking.utils.LogUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import org.slf4j.Logger;

import java.util.Map;

public class Hooks {

    private static final Logger log = LogUtils.getLogger(Hooks.class);
    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before("@requiresAuth")
    public void setupAuth() {
        Response res = ApiUtils.authLogin("/auth/login", Map.of("username", "admin", "password", "password"));
        context.setAuthToken(res.jsonPath().getString("token"));
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("🚀 Starting Scenario: {}", scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            log.error("❌ Scenario Failed: {}", scenario.getName());
        } else {
            log.info("✅ Scenario Passed: {}", scenario.getName());
        }
        log.info("-------------------------------------------------------------");
    }
}
