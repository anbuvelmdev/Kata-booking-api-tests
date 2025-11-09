package com.booking.stepdefinitions;

import com.booking.constants.ConfigKeys;
import com.booking.constants.FilePaths;
import com.booking.context.TestContext;
import com.booking.pojo.BookingRequest;
import com.booking.utils.ApiUtils;
import com.booking.utils.ConfigReader;
import com.booking.utils.JsonUtils;
import com.booking.utils.LogUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;

import java.io.IOException;

public class UpdateBookingSteps {

    private static final Logger log = LogUtils.getLogger(UpdateBookingSteps.class);
    private final TestContext context;
    private Response response;
    private BookingRequest bookingRequest;

    public UpdateBookingSteps(TestContext context) {
        this.context = context;
    }

    @Given("booking with id {string} exists")
    public void booking_with_id_exists(String bookingId) {
        log.info("Checking booking ID exists: {}", bookingId);
    }

    @Given("user loads required booking data from {string}")
    public void user_loads_required_booking_data_from(String testDataKey) {
        bookingRequest = JsonUtils.loadJson(FilePaths.TESTDATA_PATH + "booking_data.json",
                                            testDataKey,
                                            BookingRequest.class);
        Assert.assertNotNull("Booking data should be loaded from JSON", bookingRequest);
        log.info("Verified booking request {}", bookingRequest);
    }

    @When("user sends a PUT request to {string} with booking details")
    public void user_sends_a_put_request_to_with_booking_details(String endpoint) throws IOException {
        String token = context.getAuthToken();
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL); // ensure ConfigKeys holds BASE_URL
        response = ApiUtils.put(baseUrl, endpoint, bookingRequest, token);
        context.setResponse(response);
        log.info("PUT request sent to booking endpoint: {}", endpoint);
    }

    @And("validate update booking response based on {string}")
    public void validate_update_booking_response_based_on(String expectedError) {
        String body = response.asString();
        log.info("Response after GET: {}", body);
        boolean isValid = body.contains(expectedError);
        Assert.assertTrue("Validation failed: " + expectedError, isValid);
    }

    @Then("validate update booking response should contain error {string}")
    public void validate_update_booking_response_should_contain_error(String expectedError) {
        String body = response.asString();
        log.info("Response after GET: {}", body);
        boolean isValid = body.contains(expectedError);
        Assert.assertTrue("Error validation failed: " + expectedError, isValid);
    }
}
