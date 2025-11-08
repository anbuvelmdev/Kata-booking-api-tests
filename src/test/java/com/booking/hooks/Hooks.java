package com.booking.hooks;

import com.booking.config.ConfigReader;
import com.booking.constants.AuthConstants;
import com.booking.constants.ConfigKeys;
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
        String endpoint = ConfigReader.get(ConfigKeys.AUTH_ENDPOINT);
        String username = ConfigReader.get(ConfigKeys.AUTH_USERNAME);
        String password = ConfigReader.get(ConfigKeys.AUTH_PASSWORD);

        Response res = ApiUtils.authLogin(endpoint, Map.of(AuthConstants.USERNAME, username, AuthConstants.PASSWORD, password));
        context.setAuthToken(res.jsonPath().getString(AuthConstants.TOKEN));
        log.info("Auth token acquired successfully");
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("Starting Scenario: {}", scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            log.error("Scenario Failed: {}", scenario.getName());
        } else {
            log.info("Scenario Passed: {}", scenario.getName());
        }
        log.info("-------------------------------------------------------------");
    }
}
