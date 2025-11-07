package com.booking.booking.stepdefinitions;

import com.booking.booking.utils.ApiUtils;
import com.booking.pojo.BookingRequest;
import com.booking.pojo.BookingResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import com.booking.utils.JsonUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class BookingSteps {

    private Response response;
    private Map<String, String> credentials;
    private String authToken;
    private BookingRequest bookingRequest;
    private ApiUtils bookingApi;
    private BookingResponse bookingResponse;
    private int bookingId;

    @Given("user have valid booking data")
    public void user_have_valid_booking_data(){
        bookingRequest = JsonUtils.loadBookingData("src/test/resources/testdata/booking_data.json");
        bookingRequest.setRoomid(new Random().nextInt(100) + 1);
        Assert.assertNotNull("Booking data should be loaded from JSON", bookingRequest);
        System.out.println(bookingRequest);
    }

    @When("user send a POST request to {string} with booking details")
    public void user_send_a_post_request_to_with_booking_details(String endpoint) throws IOException {

        bookingApi = new ApiUtils();
        response = bookingApi.postBookingWithoutAuth(endpoint, bookingRequest);
        // deserialize to POJO for your assertions
        bookingResponse = response.as(BookingResponse.class);
        System.out.println("POST request sent to create booking");
    }

    @Then("user should receive status code {int}")
    public void user_should_receive_status_code(int expectedStatus) {
        Assert.assertEquals(expectedStatus, response.statusCode());
    }

    @Then("the response should contain the booking id")
    public void the_response_should_contain_the_booking_id() {
        bookingId = bookingResponse.getBookingid();
        Assert.assertTrue("Booking ID should be greater than 0",bookingResponse.getBookingid() > 0);
    }

    @Then("the error message should contain {string}")
    public void the_error_message_should_contain(String expectedMessage) {
        String message = response.getBody().asString();
        Assert.assertTrue("Expected error message not found", message.contains(expectedMessage));
    }
}
