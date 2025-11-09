package com.booking.stepdefinitions;

import com.booking.constants.ConfigKeys;
import com.booking.utils.ApiUtils;
import com.booking.utils.ConfigReader;
import com.booking.utils.Context;
import com.booking.utils.LogUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

public class DeleteBookingSteps {

    private static final Logger log = LogUtils.getLogger(DeleteBookingSteps.class);
    private final Context context;
    private String bookingId;
    private Response response;

    public DeleteBookingSteps(Context context) {
        this.context = context;
    }

    @Given("a booking ID {string}")
    public void a_booking_ID(String id) {
        this.bookingId = id;
        log.info("Target booking ID to delete: {}", bookingId);
    }

    @When("user sends DELETE request to {string}")
    public void user_sends_DELETE_request(String endpoint) {
        sendDeleteRequest(endpoint, context.getAuthToken());
    }

    @When("user sends DELETE request unauthorized to {string}")
    public void user_sends_DELETE_request_unauthorized_to(String endpoint) {
        sendDeleteRequest(endpoint, "");
    }

    @When("user send a DELETE request invalid token to {string}")
    public void user_send_a_DELETE_request_invalid_token_to(String endpoint) {
        sendDeleteRequest(endpoint, ConfigReader.get(ConfigKeys.DUMMY_TOKEN));
    }

    private void sendDeleteRequest(String endpoint, String token) {
        String resolvedEndpoint = endpoint + bookingId;
        log.info("Sending DELETE request to endpoint: {}", resolvedEndpoint);

        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL);
        response = ApiUtils.delete(baseUrl, resolvedEndpoint, token);
        context.setResponse(response);
    }

    @Then("the booking should be removed successfully and contain {string}")
    public void the_booking_should_be_removed_successfully_and_contain(String validationType) {
        String body = context.getResponse().asString();
        log.info("Response after DELETE: {}", body);

        boolean isDeleted = body.contains("Deleted");
        boolean isEmpty = body.isEmpty();
        boolean matchesExpected = body.contains(validationType);

        Assertions.assertTrue(
                isDeleted || isEmpty || matchesExpected,
                String.format("Unexpected delete response: '%s'", body)
        );
    }
}
