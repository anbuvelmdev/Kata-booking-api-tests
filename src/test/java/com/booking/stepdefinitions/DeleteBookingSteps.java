package com.booking.stepdefinitions;

import com.booking.constants.ConfigKeys;
import com.booking.context.TestContext;
import com.booking.utils.ApiUtils;
import com.booking.utils.ConfigReader;
import com.booking.utils.LogUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

public class DeleteBookingSteps {

    private static final Logger log = LogUtils.getLogger(DeleteBookingSteps.class);
    private final TestContext context;
    private String bookingId;
    private Response response;

    public DeleteBookingSteps(TestContext context) {
        this.context = context;
    }

    @Given("a booking ID {string}")
    public void a_booking_ID(String id) {
        this.bookingId = id;
        log.info("Target booking ID to delete: {}", bookingId);
    }

    @When("user sends DELETE request to {string}")
    public void user_sends_DELETE_request(String endpoint) {
        String resolvedEndpoint = endpoint + bookingId;
        log.info("Sending DELETE valid request to endpoint: {}", resolvedEndpoint);

        String token = context.getAuthToken();
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.delete(baseUrl, resolvedEndpoint, token);
        context.setResponse(response);
        log.info("DELETE request sent: {}", bookingId);
    }

    @When("user sends DELETE request unauthorized to {string}")
    public void user_sends_DELETE_request_unauthorized_to(String endpoint) {
        String resolvedEndpoint = endpoint + bookingId;
        log.info("Sending DELETE unauthorized request to endpoint: {}", resolvedEndpoint);

        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.delete(baseUrl, resolvedEndpoint, "");
        context.setResponse(response);
        log.info("DELETE request sent without token: {}", bookingId);
    }

    @When("user send a DELETE request invalid token to {string}")
    public void user_send_a_DELETE_request_invalid_token_to(String endpoint) {
        String resolvedEndpoint = endpoint + bookingId;
        log.info("Sending DELETE invalid request to endpoint: {}", resolvedEndpoint);

        String token = ConfigReader.get(ConfigKeys.DUMMY_TOKEN);
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.delete(baseUrl, resolvedEndpoint, token);
        context.setResponse(response);
        log.info("DELETE request sent invalid token: {}", bookingId);
    }

    @Then("the booking should be removed successfully and contain {string}")
    public void the_booking_should_be_removed_successfully_and_contain(String validationType) {
        String body = context.getResponse().asString();
        log.info("Response after DELETE: {}", body);
        Assertions.assertTrue(body.contains("Deleted") || body.isEmpty() || body.contains(validationType),
                              "Booking deletion confirmation not found in response");
    }

    @Then("delete response should contain error {string}")
    public void delete_response_should_contain_error(String expectedError) {
        String body = response.asString();
        log.info("Error Response after DELETE: {}", body);
        boolean isValid = body.contains(expectedError);
        Assert.assertTrue("Error validation failed: " + expectedError, isValid);
    }
}
