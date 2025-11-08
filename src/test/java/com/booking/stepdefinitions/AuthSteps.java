package com.booking.stepdefinitions;

import com.booking.config.ConfigReader;
import com.booking.constants.AuthConstants;
import com.booking.constants.ConfigKeys;
import com.booking.utils.ApiUtils;
import com.booking.context.TestContext;
import com.booking.utils.ResponseValidator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthSteps {
    private Response response;
    private static String authToken;
    private Map<String, String> credentials;

    private final TestContext context;
    public AuthSteps(TestContext context) {
        this.context = context;
    }

    @Given("login using username {string} and password {string}")
    public void login_using_username_and_password(String username, String password) {
        response = ApiUtils.authLogin(ConfigReader.get(ConfigKeys.AUTH_ENDPOINT), Map.of(AuthConstants.USERNAME, username, AuthConstants.PASSWORD, password));
        String token = response.jsonPath().getString(AuthConstants.TOKEN);
        context.setAuthToken(token);
    }

    @Then("the response auth login status code should {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        ResponseValidator.validateStatusCode(response, expectedStatusCode);
    }

    @Then("the response should contain a token or error {string}")
    public void verifyToken(String expectedError) {
        authToken = response.jsonPath().getString(AuthConstants.TOKEN);
        String error = response.jsonPath().getString(AuthConstants.ERROR);

        if (expectedError.isEmpty()){
            assertThat(authToken, notNullValue());
        }else{
            assertThat(error, notNullValue());
        }
    }

    public static String getAuthToken() {
        return authToken;
    }
}
