package com.booking.stepdefinitions;

import com.booking.utils.ApiUtils;
import com.booking.context.TestContext;
import com.booking.pojo.BookingRequest;
import com.booking.pojo.BookingResponse;
import com.booking.utils.LogUtils;
import com.booking.utils.ResponseValidator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import com.booking.utils.JsonUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Random;

public class BookingSteps {

    private Response response;
    private BookingRequest bookingRequest;
    private ApiUtils bookingApi;
    private BookingResponse bookingResponse;
    private int bookingId;
    private final TestContext context;
    private static final Logger log = LogUtils.getLogger(UpdateBookingSteps.class);
    public BookingSteps(TestContext context) {
        this.context = context;
    }

    @Given("user loads booking data from {string}")
    public void user_loads_booking_data_from(String dataFile){
        bookingRequest = JsonUtils.loadBookingData("src/test/resources/testdata/" + dataFile);
        bookingRequest.setRoomid(new Random().nextInt(100) + 1); // For dynamic roomID
        Assert.assertNotNull("Booking data should be loaded from JSON", bookingRequest);
        log.info("Verified booking request {}", bookingRequest);
    }

    @When("user sends a POST request to {string} with booking details")
    public void user_sends_a_post_request_to_with_booking_details(String endpoint) throws IOException {

        bookingApi = new ApiUtils();
        response = bookingApi.postBookingWithoutAuth(endpoint, bookingRequest);
        // deserialize to POJO for your assertions
        bookingResponse = response.as(BookingResponse.class);
        log.info("POST request sent to create booking");
    }

    @Then("user should receive booking status code {int}")
    public void user_should_receive_booking_status_code(int expectedStatusCode) {
        ResponseValidator.validateStatusCode(response, expectedStatusCode);
    }

    @And("the response should contain the booking id")
    public void the_response_should_contain_the_booking_id() {
        bookingId = bookingResponse.getBookingid();
        Assert.assertTrue("Booking ID should be greater than 0",bookingResponse.getBookingid() > 0);
    }

    @And("validate response based on {string}")
    public void validateResponse(String type) {
        String body = response.asString();
        boolean isValid = switch (type.toLowerCase()) {
            case "validbooking" -> body.contains("bookingid");
            case "missingemailphone" -> body.contains("Failed");
            case "missingfirstname" -> body.contains("Firstname");
            case "invalidemail" -> body.contains("email");
            case "invaliddates" -> body.contains("Failed");
            default -> true; // no validation
        };
        Assert.assertTrue("Validation failed for type: " + type, isValid);
    }
}
