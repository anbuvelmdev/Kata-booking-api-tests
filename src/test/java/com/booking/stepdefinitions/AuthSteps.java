package com.booking.stepdefinitions;

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
        response = ApiUtils.authLogin("/auth/login", Map.of("username", username, "password", password));
        String token = response.jsonPath().getString("token");
        context.setAuthToken(token);
    }

    @Then("the response auth login status code should {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        ResponseValidator.validateStatusCode(response, expectedStatusCode);
    }

    @Then("the response should contain a token or error {string}")
    public void verifyToken(String expectedError) {
        authToken = response.jsonPath().getString("token");
        String error = response.jsonPath().getString("error");

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
