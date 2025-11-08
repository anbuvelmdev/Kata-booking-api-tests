package com.booking.stepdefinitions;

import com.booking.pojo.BookingResponse;
import com.booking.utils.ApiUtils;
import com.booking.context.TestContext;
import com.booking.utils.LogUtils;
import com.booking.utils.ResponseValidator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;

import static com.booking.constants.BookingResponseKeys.*;

public class GetBookingSteps {

    private Response response;
    private ApiUtils bookingApi;
    private int bookingId;
    private BookingResponse bookingResponse;
    private final TestContext context;
    private static final Logger log = LogUtils.getLogger(GetBookingSteps.class);
    public GetBookingSteps(TestContext context) {
        this.context = context;
    }
    @When("user send a GET request to {string}")
    public void user_send_a_get_request_to(String endpoint) {
        String token = context.getAuthToken();
        response = ApiUtils.getBooking(endpoint, token);
    }

    @When("user send a GET request invalid token to {string}")
    public void user_send_a_GET_request_invalid_token_to(String endpoint) {
        String token = "invalid-token-123456";
        response = ApiUtils.getBooking(endpoint, token);
    }

    @When("user send a GET request to {string} without token")
    public void user_send_a_get_request_without_token(String endpoint) {
        bookingApi = new ApiUtils();
        response = ApiUtils.getBookingWithoutAuth(endpoint);
    }

    @Then("user get response booking status code should be {int}")
    public void user_get_response_booking_status_code_should_be(int expectedStatusCode) {
        ResponseValidator.validateStatusCode(response, expectedStatusCode);
    }

    @And("get response should contain the booking id")
    public void the_response_should_contain_the_booking_id() {
        bookingId = bookingResponse.getBookingid();
        Assert.assertTrue("Booking ID should be greater than 0",bookingResponse.getBookingid() > 0);
    }

    @And("validate get response should contain booking details")
    public void validate_get_response_should_contain_booking_details() {
        Assert.assertNotNull(response.jsonPath().get(FIRSTNAME));
        Assert.assertNotNull(response.jsonPath().get(LASTNAME));
        Assert.assertNotNull(response.jsonPath().get(BOOKINGDATES_CHECKIN));
        Assert.assertNotNull(response.jsonPath().get(BOOKINGDATES_CHECKOUT));
    }

    @And("validate error message should contain {string}")
    public void validate_error_message_should_contain(String expectedMessage) {
        String body = response.getBody().asString();
        log.info("Response after GET: {}", body);
        Assert.assertTrue("Expected message not found", body.contains(expectedMessage));
    }
}