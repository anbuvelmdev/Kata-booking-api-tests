package com.booking.stepdefinitions;

import com.booking.config.ConfigReader;
import com.booking.constants.BookingResponseKeys;
import com.booking.constants.FilePaths;
import com.booking.constants.ConfigKeys;
import com.booking.utils.ApiUtils;
import com.booking.context.TestContext;
import com.booking.pojo.BookingRequest;
import com.booking.utils.JsonUtils;
import com.booking.utils.LogUtils;
import com.booking.utils.ResponseValidator;
import io.restassured.response.Response;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Random;

public class UpdateBookingSteps {

    private Response response;
    private String authToken;
    private BookingRequest bookingRequest;
    private ApiUtils bookingApi;
    private final TestContext context;
    private static final Logger log = LogUtils.getLogger(UpdateBookingSteps.class);
    public UpdateBookingSteps(TestContext context) {
        this.context = context;
    }

    @Given("booking with id {string} exists")
    public void booking_with_id_exists(String bookingId) {
       log.info("Checking booking ID exists: {}", bookingId);
    }

    @Given("user loads required booking data from {string}")
    public void user_loads_required_booking_data_from(String dataFile){
        bookingRequest = JsonUtils.loadBookingData(FilePaths.TESTDATA_PATH + dataFile);
        bookingRequest.setRoomid(new Random().nextInt(100) + 1); // For dynamic roomID
        Assert.assertNotNull("Booking data should be loaded from JSON", bookingRequest);
        log.info("Verified booking request {}", bookingRequest);
    }

    @When("user sends a PUT request to {string} with booking details")
    public void user_sends_a_put_request_to_with_booking_details(String endpoint) throws IOException {
        String token = context.getAuthToken();
        bookingApi = new ApiUtils();
        response = ApiUtils.putRequest(endpoint, bookingRequest, token);
        log.info("PUT request sent to create booking");
    }

    @Then("user response update booking status code should be {int}")
    public void the_response_update_booking_status_code_should_be(int expectedStatusCode) {
        ResponseValidator.validateStatusCode(response, expectedStatusCode);
    }

    @Then("response should contain updated booking details")
    public void response_should_contain_updated_booking_details() {
        String firstName = response.jsonPath().getString(BookingResponseKeys.FIRSTNAME);
        Assert.assertEquals("John", firstName);
    }
}
