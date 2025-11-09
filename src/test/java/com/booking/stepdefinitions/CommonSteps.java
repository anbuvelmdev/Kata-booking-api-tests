package com.booking.stepdefinitions;

import com.booking.constants.FilePaths;
import com.booking.pojo.BookingRequest;
import com.booking.utils.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.slf4j.Logger;

import java.time.LocalDate;

import static com.booking.constants.BookingResponseKeys.*;

public class CommonSteps {

    private static final Logger log = LogUtils.getLogger(CommonSteps.class);
    private final Context context;

    public CommonSteps(Context context) {
        this.context = context;
    }

    @Given("user loads required booking data from {string}")
    public void user_loads_required_booking_data_from(String testDataKey) {
        BookingRequest bookingRequest = JsonUtils.loadJson(FilePaths.TESTDATA_PATH + "booking_data.json",
                                                           testDataKey,
                                                           BookingRequest.class);
        context.setTestDataKey(testDataKey);
        Assert.assertNotNull("Booking data should be loaded from JSON", bookingRequest);
        context.setBookingRequest(bookingRequest);
        log.info("Loaded booking request: {}", bookingRequest);
    }

    @And("validate response based on {string}")
    public void validateResponse(String validationType) {
        Response response = context.getResponse();
        String body = response.asString();
        log.info("Response body: {}", body);

        Assert.assertTrue(
                "Validation failed. Expected text not found: " + validationType,
                StringUtils.containsIgnoreCase(body, validationType));
    }

    @And("validate response should contain error {string}")
    public void validate_response_should_contain_error(String expectedError) {
        Response response = context.getResponse();
        String body = response.asString();
        log.warn("Response (error): {}", body);
        Assert.assertTrue(
                "Error validation failed: " + expectedError,
                StringUtils.containsIgnoreCase(body, expectedError)
        );
    }

    @And("response should match schema {string}")
    public void response_should_match_schema(String schemaFileName) {
        if (schemaFileName != null && !schemaFileName.trim().isEmpty()) {
            SchemaValidator.validateSchema(context.getResponse(), schemaFileName);
        }
        log.info("Schema validation SKIPPED");
    }

    @Then("user should receive status code {int}")
    public void user_should_receive_status_code(int expectedStatusCode) {
        ResponseValidator.validateStatusCode(context.getResponse(), expectedStatusCode);
    }

    @And("validate response should contain booking details")
    public void validate_response_should_contain_booking_details() {
        Response response = context.getResponse();
        String body = response.getBody().asString();
        Assert.assertFalse("Response body should not be empty", body.isEmpty());

        log.info("Validating booking details...");

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
        Assert.assertNotNull("DepositPaid should not be null", depositPaid);

        LocalDate checkinDate = LocalDate.parse(response.jsonPath().get(BOOKINGDATES_CHECKIN));
        LocalDate checkoutDate = LocalDate.parse(response.jsonPath().get(BOOKINGDATES_CHECKOUT));
        Assert.assertTrue("Checkout date should be after check-in date", checkoutDate.isAfter(checkinDate));

        log.info("Booking response fields validated successfully.");
    }
}
