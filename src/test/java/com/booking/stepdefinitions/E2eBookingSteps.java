package com.booking.stepdefinitions;

import com.booking.constants.ConfigKeys;
import com.booking.pojo.BookingDates;
import com.booking.pojo.BookingRequest;
import com.booking.pojo.BookingResponse;
import com.booking.utils.ApiUtils;
import com.booking.utils.ConfigReader;
import com.booking.utils.Context;
import com.booking.utils.LogUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Random;

public class E2eBookingSteps {

    private static final Logger log = LogUtils.getLogger(E2eBookingSteps.class);
    private final Context context;

    public E2eBookingSteps(Context context) {
        this.context = context;
    }

    @When("create a new booking with the following details:")
    public void createNewBooking(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        int roomId = new Random().nextInt(1000) + 1; // generates 1–1000 inclusive
        BookingRequest bookingRequest = new BookingRequest(
                roomId,
                data.get("firstname"),
                data.get("lastname"),
                data.get("email"),
                data.get("phone"),
                Boolean.parseBoolean(data.get("depositpaid")),
                new BookingDates(data.get("checkin"), data.get("checkout"))
        );
        String baseUrl = ConfigReader.get(ConfigKeys.BASE_URL);
        String endpoint = "/booking";
        log.info("Sending POST request to create booking at endpoint: {}", endpoint);
        Response response = ApiUtils.post(baseUrl, endpoint, bookingRequest, null);
        context.setResponse(response);

        // deserialize to POJO for the assertions
        BookingResponse bookingResponse = response.as(BookingResponse.class);
        int bookingId = bookingResponse.getBookingid();
        log.info("Booking created successfully. Booking ID: {}",
                 bookingId != 0 ? bookingId : "N/A");
        context.setBookingId(bookingId);
    }

    @And("store the booking ID for later use")
    public void storeBookingId() {
        Response response = context.getResponse();
        // Deserialize response to POJO
        BookingResponse bookingResponse = response.as(BookingResponse.class);
        Assert.assertNotNull("Booking response should not be null", bookingResponse);

        int bookingId = bookingResponse.getBookingid();
        log.info("Validate booking ID in response: {}", bookingId);
        context.setBookingId(bookingId);
        Assert.assertTrue("Booking ID should be greater than 0", bookingId > 0);
    }

    @When("retrieve the booking by ID")
    public void getBookingById() {
        String endpoint = "/booking/" + context.getBookingId();
        Response response = ApiUtils.get(ConfigReader.get(ConfigKeys.BASE_URL),
                                         endpoint,
                                         context.getAuthToken());
        context.setResponse(response);
        log.info("GET request sent to endpoint: {}", endpoint);
    }

    @When("update the booking with:")
    public void updateBooking(DataTable dataTable) {
        String endpoint = "/booking/" + context.getBookingId();
        Map<String, String> data = dataTable.asMaps().get(0);
        int roomId = new Random().nextInt(1000) + 1; // generates 1–1000 inclusive
        BookingRequest bookingRequest = new BookingRequest(
                roomId,
                data.get("firstname"),
                data.get("lastname"),
                data.get("email"),
                data.get("phone"),
                Boolean.parseBoolean(data.get("depositpaid")),
                new BookingDates(data.get("checkin"), data.get("checkout"))
        );
        Response response = ApiUtils.put(ConfigReader.get(ConfigKeys.BASE_URL),
                                         endpoint,
                                         bookingRequest,
                                         context.getAuthToken());
        context.setResponse(response);
        log.info("PUT request sent to endpoint: {}", endpoint);
    }

    @When("delete the booking by ID")
    public void deleteBooking() {
        String endpoint = "/booking/" + context.getBookingId();
        Response response = ApiUtils.delete(ConfigReader.get(ConfigKeys.BASE_URL),
                                            endpoint,
                                            context.getAuthToken());
        context.setResponse(response);
        log.info("DELETE request sent to endpoint: {}", endpoint);
    }

    @Then("verify the booking no longer exists")
    public void verifyBookingDeleted() {
        String endpoint = "/booking/" + context.getBookingId();
        Response getResponse = ApiUtils.get(ConfigReader.get(ConfigKeys.BASE_URL),
                                            endpoint,
                                            context.getAuthToken());
        log.info("GET request sent to verify booking ID deleted: {}", endpoint);
        Assert.assertEquals(404, getResponse.getStatusCode());
    }
}
