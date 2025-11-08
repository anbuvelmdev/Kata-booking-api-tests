package com.booking.stepdefinitions;

import com.booking.config.ConfigReader;
import com.booking.constants.AuthConstants;
import com.booking.constants.ConfigKeys;
import com.booking.constants.FilePaths;
import com.booking.utils.*;
import com.booking.context.TestContext;
import com.booking.pojo.BookingRequest;
import com.booking.pojo.BookingResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Map;
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
        bookingRequest = JsonUtils.loadJson(FilePaths.TESTDATA_PATH + "booking_data.json", testDataKey, BookingRequest.class);

        bookingRequest.setRoomid(new Random().nextInt(100) + 1); // For dynamic roomID
        Assert.assertNotNull("Booking data should be loaded from JSON", bookingRequest);
        log.info("Verified booking request {}", bookingRequest);
    }

    @When("user sends a POST request to {string} with booking details")
    public void user_sends_a_post_request_to_with_booking_details(String endpoint) throws IOException {
        String token = context.getAuthToken();
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.post(baseUrl, endpoint,
                bookingRequest,
                token);
        context.setResponse(response);
        // deserialize to POJO for the assertions
        bookingResponse = response.as(BookingResponse.class);
        log.info("POST request sent to create booking");
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
