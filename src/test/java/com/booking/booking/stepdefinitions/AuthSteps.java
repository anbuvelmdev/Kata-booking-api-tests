package com.booking.booking.stepdefinitions;

import com.booking.booking.utils.ApiUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthSteps {
    private Response response;
    private Map<String, String> credentials;

    @Given("I have username {string} and password {string}")
    public void setCredentials(String username, String password) {
        credentials = Map.of("username", username, "password", password);
    }

    @When("I send a POST request to {string}")
    public void sendPostRequest(String authEndpoint) {
        response = ApiUtils.authLogin(authEndpoint, credentials);
        response.then().log().all();
    }

    @Then("the response status code should {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        assertThat(response.getStatusCode(), equalTo(expectedStatusCode));
    }

    @Then("the response should contain a token or error {string}")
    public void verifyToken(String expectedError) {
        String token = response.jsonPath().getString("token");
        String error = response.jsonPath().getString("error");

        if (expectedError.isEmpty()){
            assertThat(token, notNullValue());
        }else{
            assertThat(error, notNullValue());
        }
    }
}
