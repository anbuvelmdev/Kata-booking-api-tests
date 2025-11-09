package com.booking.stepdefinitions;

import com.booking.constants.ConfigKeys;
import com.booking.context.TestContext;
import com.booking.pojo.BookingResponse;
import com.booking.utils.ApiUtils;
import com.booking.utils.ConfigReader;
import com.booking.utils.LogUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;

public class GetBookingSteps {

    private static final Logger log = LogUtils.getLogger(GetBookingSteps.class);
    private final TestContext context;
    private Response response;
    private int bookingId;
    private BookingResponse bookingResponse;

    public GetBookingSteps(TestContext context) {
        this.context = context;
    }

    @When("user send a GET request to {string}")
    public void user_send_a_get_request_to(String endpoint) {
        String token = context.getAuthToken();
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.get(baseUrl, endpoint, token);
        context.setResponse(response);
        log.info("GET request sent: {}", endpoint);
    }

    @When("user send a GET request invalid token to {string}")
    public void user_send_a_GET_request_invalid_token_to(String endpoint) {
        String token = ConfigReader.get(ConfigKeys.DUMMY_TOKEN);
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.get(baseUrl, endpoint, token);
        context.setResponse(response);
        log.info("GET request sent invalid token: {}", endpoint);
    }

    @When("user send a GET request to {string} without token")
    public void user_send_a_get_request_without_token(String endpoint) {
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.get(baseUrl, endpoint, "");
        context.setResponse(response);
        log.info("GET request sent without token: {}", endpoint);
    }

    @And("get response should contain the booking id")
    public void the_response_should_contain_the_booking_id() {
        bookingId = bookingResponse.getBookingid();
        Assert.assertTrue("Booking ID should be greater than 0", bookingResponse.getBookingid() > 0);
    }

    @And("validate error message should contain {string}")
    public void validate_error_message_should_contain(String expectedMessage) {
        String body = response.getBody().asString();
        log.info("Response after GET: {}", body);
        Assert.assertTrue("Expected message not found", body.contains(expectedMessage));
    }
}