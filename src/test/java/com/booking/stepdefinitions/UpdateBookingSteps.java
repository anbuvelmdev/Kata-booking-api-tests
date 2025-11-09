package com.booking.stepdefinitions;

import com.booking.constants.ConfigKeys;
import com.booking.pojo.BookingRequest;
import com.booking.utils.ApiUtils;
import com.booking.utils.ConfigReader;
import com.booking.utils.Context;
import com.booking.utils.LogUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;

public class UpdateBookingSteps {

    private static final Logger log = LogUtils.getLogger(UpdateBookingSteps.class);
    private final Context context;

    public UpdateBookingSteps(Context context) {
        this.context = context;
    }

    @Given("booking with id {string} exists")
    public void booking_with_id_exists(String bookingId) {
        Assert.assertNotNull("Booking ID should not be null", bookingId);
//        context.setBookingId(bookingId);
        log.info("Confirmed booking ID exists: {}", bookingId);
    }

    @When("user sends a PUT request to {string} with booking details")
    public void user_sends_a_put_request_to_with_booking_details(String endpoint) {
        String token = context.getAuthToken();
        BookingRequest bookingRequest = context.getBookingRequest();
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL);
        Assert.assertNotNull("Booking request data should be loaded before PUT", bookingRequest);
        Assert.assertNotNull("Auth token should not be null for update", token);

        endpoint = endpoint + String.valueOf(context.getBookingId());
        Response response = ApiUtils.put(baseUrl, endpoint, bookingRequest, token);
        context.setResponse(response);
        log.info("PUT request sent to endpoint: {}", endpoint);
        log.debug("Updated booking payload: {}", bookingRequest);
    }
}
