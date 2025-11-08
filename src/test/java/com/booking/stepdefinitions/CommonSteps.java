package com.booking.booking.stepdefinitions;

import com.booking.utils.ResponseValidator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import com.booking.utils.SchemaValidator;
import com.booking.context.TestContext;

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
}
