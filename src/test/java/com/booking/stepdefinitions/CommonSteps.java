package com.booking.booking.stepdefinitions;

import com.booking.utils.ResponseValidator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import com.booking.utils.SchemaValidator;
import com.booking.context.TestContext;
import org.junit.Assert;

import java.time.LocalDate;

import static com.booking.constants.BookingResponseKeys.*;
import static com.booking.constants.BookingResponseKeys.BOOKINGDATES_CHECKIN;
import static com.booking.constants.BookingResponseKeys.BOOKINGDATES_CHECKOUT;
import static com.booking.constants.BookingResponseKeys.DEPOSITPAID;
import static com.booking.constants.BookingResponseKeys.LASTNAME;

public class CommonSteps {

    private final TestContext context;

    public CommonSteps(TestContext context) {
        this.context = context;
    }

    @And("response should match schema {string}")
    public void response_should_match_schema(String schemaFileName) {
        Response response = context.getResponse();
        SchemaValidator.validateSchema(response, schemaFileName);
    }

    @Then("user should receive status code {int}")
    public void user_should_receive_status_code(int expectedStatusCode) {
        Response response = context.getResponse();
        ResponseValidator.validateStatusCode(response, expectedStatusCode);
    }

    @And("validate response should contain booking details")
    public void validate_response_should_contain_booking_details() {
        Response response = context.getResponse();
        String body = response.getBody().asString();
        Assert.assertFalse("Response body should not be empty", body.isEmpty());

        int bookingId = response.jsonPath().getInt(BOOKINGID);
        int roomId = response.jsonPath().getInt(ROOMID);
        Assert.assertTrue("Booking ID should be greater than 0", bookingId > 0);
        Assert.assertTrue("Room ID should be greater than 0", roomId > 0);

        String firstName = response.jsonPath().getString(FIRSTNAME);
        String lastName = response.jsonPath().getString(LASTNAME);
        Assert.assertNotNull(firstName);
        Assert.assertFalse("Firstname should not be empty", firstName.isEmpty());
        Assert.assertNotNull(lastName);
        Assert.assertFalse("Lastname should not be empty", lastName.isEmpty());

        Boolean depositPaid = response.jsonPath().getBoolean(DEPOSITPAID);
        Assert.assertNotNull(depositPaid);

        LocalDate checkinDate = LocalDate.parse(response.jsonPath().get(BOOKINGDATES_CHECKIN));
        LocalDate checkoutDate = LocalDate.parse(response.jsonPath().get(BOOKINGDATES_CHECKOUT));
        Assert.assertNotNull(checkinDate);
        Assert.assertNotNull(checkoutDate);
        Assert.assertTrue("Checkout date should be after check-in date",
                checkoutDate.isAfter(checkinDate));
    }
}
