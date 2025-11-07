package com.booking.stepdefinitions;

import com.booking.utils.ApiUtils;
import com.booking.context.TestContext;
import com.booking.pojo.BookingRequest;
import com.booking.utils.LogUtils;
import com.booking.utils.ResponseValidator;
import io.restassured.response.Response;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.slf4j.Logger;

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

    @When("user updates booking {string} with new details")
    public void user_updates_booking_with_new_details(String bookingId) {
        String token = context.getAuthToken();
        bookingRequest = new BookingRequest();
        bookingRequest.setBookingId(Integer.parseInt(bookingId));
        bookingRequest.setRoomid(444);
        bookingRequest.setFirstname("John");
        bookingRequest.setLastname("Doe");
        bookingRequest.setDepositpaid(true);
        bookingRequest.setBookingdates(new BookingRequest.BookingDates("2025-11-10", "2025-11-15"));

        bookingApi = new ApiUtils();
        response = ApiUtils.putRequest("/booking/" + bookingId, bookingRequest, token);
    }

    @Then("the response update booking status code should be {int}")
    public void the_response_update_booking_status_code_should_be(int expectedStatusCode) {
        ResponseValidator.validateStatusCode(response, expectedStatusCode);
    }

    @Then("response should contain updated booking details")
    public void response_should_contain_updated_booking_details() {
        String firstName = response.jsonPath().getString("firstname");
        Assert.assertEquals("John", firstName);
    }
}
