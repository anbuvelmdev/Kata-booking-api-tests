package com.booking.hooks;

import com.booking.constants.AuthConstants;
import com.booking.constants.ConfigKeys;
import com.booking.utils.Context;
import com.booking.utils.ApiUtils;
import com.booking.utils.ConfigReader;
import com.booking.utils.LogUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import org.slf4j.Logger;

import java.util.Map;

public class Hooks {

    private static final Logger log = LogUtils.getLogger(Hooks.class);
    private final Context context;

    public Hooks(Context context) {
        this.context = context;
    }

    @Before("@requiresAuth")
    public void setupAuth() {
        String endpoint = ConfigReader.get(ConfigKeys.AUTH_ENDPOINT);
        String username = ConfigReader.get(ConfigKeys.AUTH_USERNAME);
        String password = ConfigReader.get(ConfigKeys.AUTH_PASSWORD);

        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        Response res = ApiUtils.post(baseUrl,
                                     ConfigReader.get(ConfigKeys.AUTH_ENDPOINT),
                                     Map.of(AuthConstants.USERNAME, username, AuthConstants.PASSWORD, password),
                                     null);
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
