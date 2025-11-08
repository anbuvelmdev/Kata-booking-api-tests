package com.booking.stepdefinitions;

import com.booking.utils.ApiUtils;
import com.booking.context.TestContext;
import com.booking.utils.ResponseValidator;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import static com.booking.constants.BookingResponseKeys.*;

public class GetBookingSteps {

    private Response response;
    private ApiUtils bookingApi;
    private final TestContext context;

    public GetBookingSteps(TestContext context) {
        this.context = context;
    }
    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        String token = context.getAuthToken();
        response = ApiUtils.getBooking(endpoint, token);
    }

    @When("I send a GET request to {string} without token")
    public void i_send_a_get_request_without_token(String endpoint) {
        bookingApi = new ApiUtils();
        response = ApiUtils.getBookingWithoutAuth(endpoint);
    }

    @Then("the response get booking status code should be {int}")
    public void the_response_get_booking_status_code_should_be(int expectedStatusCode) {
        ResponseValidator.validateStatusCode(response, expectedStatusCode);
    }

    @Then("the response should contain booking details with valid fields")
    public void the_response_should_contain_booking_details_with_valid_fields() {
        Assert.assertNotNull(response.jsonPath().get(FIRSTNAME));
        Assert.assertNotNull(response.jsonPath().get(LASTNAME));
        Assert.assertNotNull(response.jsonPath().get(BOOKINGDATES_CHECKIN));
        Assert.assertNotNull(response.jsonPath().get(BOOKINGDATES_CHECKOUT));
    }

    @Then("the error message should contain {string}")
    public void the_error_message_should_contain(String expectedMessage) {
        String message = response.getBody().asString();
        Assert.assertTrue("Expected message not found", message.contains(expectedMessage));
    }
}