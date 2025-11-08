package com.booking.stepdefinitions;

import com.booking.config.ConfigReader;
import com.booking.constants.AuthConstants;
import com.booking.constants.ConfigKeys;
import com.booking.pojo.BookingResponse;
import com.booking.utils.ApiUtils;
import com.booking.context.TestContext;
import com.booking.utils.LogUtils;
import com.booking.utils.ResponseValidator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthSteps {
    private Response response;
    private static String authToken;
    private Map<String, String> credentials;
    private static final Logger log = LogUtils.getLogger(AuthSteps.class);
    private final TestContext context;
    public AuthSteps(TestContext context) {
        this.context = context;
    }

    @Given("login using username {string} and password {string}")
    public void login_using_username_and_password(String username, String password) {
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
         response = ApiUtils.post(baseUrl, ConfigReader.get(ConfigKeys.AUTH_ENDPOINT),
                Map.of(AuthConstants.USERNAME, username, AuthConstants.PASSWORD, password),
                null);
        context.setResponse(response);
        log.info("POST auth request sent");
    }

    @And("validate auth token response based on {string}")
    public void validate_auth_token_response_based_on(String validationType) {
        String body = response.asString();
        log.info( body, "auth");
        boolean isValid = body.contains(validationType);
        Assert.assertTrue("Validation failed: " + validationType, isValid);
    }

    @And("validate auth token response should contain error {string}")
    public void validate_auth_token_response_should_contain_error(String expectedError) {
        String body = response.asString();
        boolean isValid = body.contains(expectedError);
        Assert.assertTrue("Error validation failed: " + expectedError, isValid);
    }


    public static String getAuthToken() {
        return authToken;
    }
}
