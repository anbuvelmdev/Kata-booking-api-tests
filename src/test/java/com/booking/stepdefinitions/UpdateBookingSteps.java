package com.booking.stepdefinitions;

import com.booking.constants.ConfigKeys;
import com.booking.pojo.BookingRequest;
import com.booking.utils.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;

import java.io.IOException;

public class UpdateBookingSteps {

    private static final Logger log = LogUtils.getLogger(UpdateBookingSteps.class);
    private final Context context;
    private Response response;
    private BookingRequest bookingRequest;

    public UpdateBookingSteps(Context context) {
        this.context = context;
    }

    @Given("booking with id {string} exists")
    public void booking_with_id_exists(String bookingId) {
        log.info("Checking booking ID exists: {}", bookingId);
    }

    @When("user sends a PUT request to {string} with booking details")
    public void user_sends_a_put_request_to_with_booking_details(String endpoint) throws IOException {
        String token = context.getAuthToken();
        bookingRequest = context.getBookingRequest();
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.put(baseUrl, endpoint, bookingRequest, token);
        context.setResponse(response);
        log.info("PUT request sent to booking endpoint: {}", endpoint);
    }
}
