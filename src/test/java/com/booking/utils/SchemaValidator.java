package com.booking.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class SchemaValidator {

    private static final String SCHEMA_PATH = "src/test/resources/schemas/";

    public static void validateSchema(Response response, String schemaFileName) {
        File schemaFile = new File(SCHEMA_PATH + schemaFileName);
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
    }
}
