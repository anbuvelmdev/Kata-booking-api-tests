package com.booking.booking.stepdefinitions;

import com.booking.booking.utils.ApiUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

public class GetBookingSteps {

    private Response response;
    private ApiUtils bookingApi;

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        bookingApi = new ApiUtils();
        response = ApiUtils.getBooking(endpoint, AuthSteps.getAuthToken());
    }

    @When("I send a GET request to {string} without token")
    public void i_send_a_get_request_without_token(String endpoint) {
        response = ApiUtils.getBookingWithoutAuth(endpoint);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        Assert.assertEquals("Unexpected status code", expectedStatusCode, response.statusCode());
    }

    @Then("the response should contain booking details with valid fields")
    public void the_response_should_contain_booking_details_with_valid_fields() {
        Assert.assertNotNull(response.jsonPath().get("firstname"));
        Assert.assertNotNull(response.jsonPath().get("lastname"));
        Assert.assertNotNull(response.jsonPath().get("bookingdates.checkin"));
        Assert.assertNotNull(response.jsonPath().get("bookingdates.checkout"));
    }

    @Then("the error message should contain {string}")
    public void the_error_message_should_contain(String expectedMessage) {
        String message = response.getBody().asString();
        Assert.assertTrue("Expected message not found", message.contains(expectedMessage));
    }
}