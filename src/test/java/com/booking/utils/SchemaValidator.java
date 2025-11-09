package com.booking.utils;

import com.booking.constants.FilePaths;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.File;

public class SchemaValidator {

    public static void validateSchema(Response response, String schemaFileName) {
        File schemaFile = new File(FilePaths.SCHEMA_PATH + schemaFileName);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
    }
}
