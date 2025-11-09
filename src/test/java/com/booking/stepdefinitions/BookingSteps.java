package com.booking.stepdefinitions;

import com.booking.constants.ConfigKeys;
import com.booking.pojo.BookingRequest;
import com.booking.pojo.BookingResponse;
import com.booking.utils.*;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;

import java.io.IOException;

public class BookingSteps {

    private static final Logger log = LogUtils.getLogger(BookingSteps.class);
    private final Context context;

    public BookingSteps(Context context) {
        this.context = context;
    }

    @When("user sends a POST request to {string} with booking details")
    public void user_sends_a_post_request_to_with_booking_details(String endpoint) {
        String token = context.getAuthToken();
        BookingRequest bookingRequest = context.getBookingRequest();
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        Response response = ApiUtils.post(baseUrl, endpoint, bookingRequest, token);
        context.setResponse(response);
        // deserialize to POJO for the assertions
        BookingResponse bookingResponse = response.as(BookingResponse.class);
        log.info("POST request sent to create booking");
    }
}
