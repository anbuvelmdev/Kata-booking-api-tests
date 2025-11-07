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

public class BookingSteps {

    private Response response;
    private Map<String, String> credentials;
    private String authToken;
    private BookingRequest bookingRequest;
    private ApiUtils bookingApi;
    private BookingResponse bookingResponse;
    private int bookingId;

    @Given("login using username {string} and password {string}")
    public void login_using_username(String username, String password) {
        credentials = Map.of("username", username, "password", password);
        response = ApiUtils.authLogin("/auth/login", credentials);
        // Extract auth token from response JSON
        authToken = response.jsonPath().getString("token");  // replace 'token' with actual JSON key
    }

    @When("I send a POST request to {string} with booking details")
    public void i_send_a_post_request_to_with_booking_details(String endpoint) throws IOException {
        System.out.println(authToken);

        bookingRequest = JsonUtils.loadBookingData("src/test/resources/testdata/booking_data.json");
        Assert.assertNotNull("Booking data should be loaded from JSON", bookingRequest);
        System.out.println(bookingRequest);

        bookingApi = new ApiUtils();
        response = bookingApi.createBooking(endpoint, bookingRequest, authToken);
        // deserialize to POJO for your assertions
        bookingResponse = response.as(BookingResponse.class);
        System.out.println("POST request sent to create booking");
    }

    @Then("I should receive status code {int}")
    public void i_should_receive_status_code(int expectedStatus) {
        Assert.assertEquals(expectedStatus, response.statusCode());
    }

    @Then("the response should contain the booking id")
    public void the_response_should_contain_the_booking_id() {
        bookingId = bookingResponse.getBookingid();
        Assert.assertTrue("Booking ID should be greater than 0",bookingResponse.getBookingid() > 0);
    }
}
