package com.booking.utils;

import com.booking.constants.FilePaths;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.slf4j.Logger;

import java.io.File;

/**
 * Utility class for validating JSON response bodies against predefined JSON Schemas.
 *
 * This ensures that the API response structure adheres to expected contracts.
 * Uses Rest-Assured's built-in {@link JsonSchemaValidator} for validation.
 */
public class SchemaValidator {

    private static final Logger log = LogUtils.getLogger(SchemaValidator.class);

    /**
     * Validates the given API response against a JSON schema file.
     *
     * @param response       The API response to validate
     * @param schemaFileName The name of the JSON schema file (e.g., "booking_response_schema.json")
     */
    public static void validateSchema(Response response, String schemaFileName) {
        File schemaFile = new File(FilePaths.SCHEMA_PATH + schemaFileName);
        log.info("Validating response against schema: {}", schemaFileName);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
        log.info("Schema validation passed for: {}", schemaFileName);
    }
}
