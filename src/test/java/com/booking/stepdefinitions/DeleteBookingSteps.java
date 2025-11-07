package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.utils.ApiUtils;
import com.booking.utils.LogUtils;
import com.booking.utils.ResponseValidator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.junit.jupiter.api.Assertions;

public class DeleteBookingSteps {

    private final TestContext context;
    private static final Logger log = LogUtils.getLogger(DeleteBookingSteps.class);
    private ApiUtils bookingApi;
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
        String resolvedEndpoint = endpoint.replace("{id}", bookingId);
        log.info("Sending DELETE request to endpoint: {}", resolvedEndpoint);

        String token = context.getAuthToken();

        bookingApi = new ApiUtils();
        response = ApiUtils.deleteRequest(resolvedEndpoint, token);
        context.setResponse(response);
    }

    @Then("the response delete status code should be {int}")
    public void the_response_delete_status_code_should_be(int expectedStatusCode) {
        ResponseValidator.validateStatusCode(response, expectedStatusCode);
    }

    @Then("the booking should be removed successfully")
    public void the_booking_should_be_removed_successfully() {
        String body = context.getResponse().asString();
        log.info("Response after DELETE: {}", body);
        Assertions.assertTrue(body.contains("Deleted") || body.isEmpty(),
                "Booking deletion confirmation not found in response");
    }
}
