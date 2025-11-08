@booking @requiresAuth
Feature: Hotel Booking API

  @createBooking @positive
  Scenario Outline: Create booking with valid data
    Given user loads booking data from "<testDataKey>"
    When user sends a POST request to "<endpoint>" with booking details
    Then user should receive booking status code <expectedStatus>
    And validate response based on "<validationType>"
    And response should match schema "<schemaFile>"

    Examples:
      | testDataKey  | endpoint | expectedStatus | validationType | schemaFile                         |
      | validBooking | /booking | 201            | bookingid      | booking_valid_response_schema.json |


  @createBooking @Negative
  Scenario Outline: Create booking with invalid or missing data
    Given user loads booking data from "<testDataKey>"
    When user sends a POST request to "<endpoint>" with booking details
    Then user should receive booking status code <expectedStatus>
    And validate response should contain error "<expectedError>"
    And response should match schema "<schemaFile>"

    Examples:
      | testDataKey             | endpoint | expectedStatus | expectedError                       | schemaFile                |
      | firstnameTooShort       | /booking | 400            | size must be between 3 and 18       | errors_array_schema.json   |
      | firstnameTooLong        | /booking | 400            | size must be between 3 and 18       | errors_array_schema.json   |
      | bookingMissingFirstname | /booking | 400            | Firstname should not be blank       | errors_array_schema.json   |
      | lastnameTooShort        | /booking | 400            | size must be between 3 and 30       | errors_array_schema.json   |
      | lastnameTooLong         | /booking | 400            | size must be between 3 and 30       | errors_array_schema.json   |
      | bookingMissingLastname  | /booking | 400            | Lastname should not be blank        | errors_array_schema.json   |
      | phoneInvalidShort       | /booking | 400            | size must be between 11 and 21      | errors_array_schema.json   |
      | phoneInvalidLong        | /booking | 400            | size must be between 11 and 21      | errors_array_schema.json   |
      | bookingInvalidEmail     | /booking | 400            | must be a well-formed email address | errors_array_schema.json   |
      | bookingInvalidDates     | /booking | 409            | Failed to create booking            | error_generic_schema.json |