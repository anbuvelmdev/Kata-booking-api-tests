package com.booking.stepdefinitions;

import com.booking.constants.AuthConstants;
import com.booking.constants.ConfigKeys;
import com.booking.utils.ApiUtils;
import com.booking.utils.ConfigReader;
import com.booking.utils.LogUtils;
import com.booking.utils.Context;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import org.slf4j.Logger;

import java.util.Map;

public class AuthSteps {
    private static final Logger log = LogUtils.getLogger(AuthSteps.class);
    private final Context context;

    public AuthSteps(Context context) {
        this.context = context;
    }

    @Given("login using username {string} and password {string}")
    public void login_using_username_and_password(String username, String password) {
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL);
        log.info("Initiating login for user: {}", username);
        Response response = ApiUtils.post(baseUrl,
                                 ConfigReader.get(ConfigKeys.AUTH_ENDPOINT),
                                 Map.of(AuthConstants.USERNAME, username, AuthConstants.PASSWORD, password),
                                 null);
        context.setResponse(response);
        log.info("Authentication request sent successfully for user: {}", username);
    }
}
