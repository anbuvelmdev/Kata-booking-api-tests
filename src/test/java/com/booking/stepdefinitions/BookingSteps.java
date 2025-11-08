package com.booking.stepdefinitions;

import com.booking.constants.FilePaths;
import com.booking.utils.*;
import com.booking.context.TestContext;
import com.booking.pojo.BookingRequest;
import com.booking.pojo.BookingResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Random;

public class BookingSteps {

    private Response response;
    private BookingRequest bookingRequest;
    private ApiUtils bookingApi;
    private BookingResponse bookingResponse;
    private final TestContext context;
    private static final Logger log = LogUtils.getLogger(BookingSteps.class);
    public BookingSteps(TestContext context) {
        this.context = context;
    }

    @Given("user loads booking data from {string}")
    public void user_loads_booking_data_from(String testDataKey){
        bookingRequest = JsonUtils.loadBookingData(FilePaths.TESTDATA_PATH + "booking_data.json", testDataKey);
        bookingRequest.setRoomid(new Random().nextInt(100) + 1); // For dynamic roomID
        Assert.assertNotNull("Booking data should be loaded from JSON", bookingRequest);
        log.info("Verified booking request {}", bookingRequest);
    }

    @When("user sends a POST request to {string} with booking details")
    public void user_sends_a_post_request_to_with_booking_details(String endpoint) throws IOException {

        bookingApi = new ApiUtils();
        response = bookingApi.postBookingWithoutAuth(endpoint, bookingRequest);
        // deserialize to POJO for the assertions
        bookingResponse = response.as(BookingResponse.class);
        log.info("POST request sent to create booking");
    }

    @Then("user should receive booking status code {int}")
    public void user_should_receive_booking_status_code(int expectedStatusCode) {
        ResponseValidator.validateStatusCode(response, expectedStatusCode);
    }

    @And("validate response based on {string}")
    public void validateResponse(String validationType) {
        String body = response.asString();
        boolean isValid = body.contains(validationType);
        Assert.assertTrue("Validation failed: " + validationType, isValid);
    }

    @And("validate response should contain error {string}")
    public void validate_response_should_contain_error(String expectedError) {
        String body = response.asString();
        log.info("Response after POST: {}", body);
        boolean isValid = body.contains(expectedError);
        Assert.assertTrue("Error validation failed: " + expectedError, isValid);
    }
}
