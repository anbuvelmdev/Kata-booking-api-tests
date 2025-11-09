package com.booking.stepdefinitions;

import com.booking.constants.ConfigKeys;
import com.booking.pojo.BookingResponse;
import com.booking.utils.ApiUtils;
import com.booking.utils.ConfigReader;
import com.booking.utils.Context;
import com.booking.utils.LogUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;

public class GetBookingSteps {

    private static final Logger log = LogUtils.getLogger(GetBookingSteps.class);
    private final Context context;
    private Response response;
    private BookingResponse bookingResponse;

    public GetBookingSteps(Context context) {
        this.context = context;
    }

    @When("user send a GET request to {string}")
    public void user_send_a_get_request_to(String endpoint) {
        String token = context.getAuthToken();
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.get(baseUrl, endpoint, token);
        context.setResponse(response);
        log.info("GET request sent to endpoint: {}", endpoint);
    }

    @When("user send a GET request invalid token to {string}")
    public void user_send_a_GET_request_invalid_token_to(String endpoint) {
        String token = ConfigReader.get(ConfigKeys.DUMMY_TOKEN);
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.get(baseUrl, endpoint, token);
        context.setResponse(response);
        log.info("GET request sent with invalid token to endpoint: {}", endpoint);
    }

    @When("user send a GET request to {string} without token")
    public void user_send_a_get_request_without_token(String endpoint) {
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.get(baseUrl, endpoint, "");
        context.setResponse(response);
        log.info("GET request sent without token to endpoint: {}", endpoint);
    }

    @And("get response should contain the booking id")
    public void the_response_should_contain_the_booking_id() {
        response = context.getResponse();

        // Deserialize response to POJO
        bookingResponse = response.as(BookingResponse.class);
        Assert.assertNotNull("Booking response should not be null", bookingResponse);

        int bookingId = bookingResponse.getBookingid();
        log.info("Validated booking ID in response: {}", bookingId);
        Assert.assertTrue("Booking ID should be greater than 0", bookingId > 0);
    }
}