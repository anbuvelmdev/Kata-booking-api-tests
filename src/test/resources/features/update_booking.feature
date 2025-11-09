@booking @requiresAuth
Feature: Update Booking API

  @updateBooking @positive
  Scenario Outline: Update booking with valid ID
    Given user loads required booking data from "<testDataKey>"
    When user sends a PUT request to "<endpoint>" with booking details
    Then user should receive status code <expectedStatus>
    And validate response based on "<validationType>"
    And response should match schema "<schemaFile>"

    Examples:
      | testDataKey        | endpoint   | expectedStatus | validationType | schemaFile                           |
      | updateValidBooking | /booking/1 | 200            | success        | success_booking_response_schema.json |

  @updateBooking @Negative
  Scenario Outline: Update booking with invalid data
    Given user loads required booking data from "<testDataKey>"
    When user sends a PUT request to "<endpoint>" with booking details
    Then user should receive status code <expectedStatus>
    And validate response should contain error "<expectedError>"
    And response should match schema "<schemaFile>"

    Examples:
      | testDataKey                   | endpoint   | expectedStatus | expectedError                       | schemaFile                |
      | updateFirstnameTooShort       | /booking/1 | 400            | size must be between 3 and 18       | errors_array_schema.json  |
      | updateFirstnameTooLong        | /booking/1 | 400            | size must be between 3 and 18       | errors_array_schema.json  |
      | updateBookingMissingFirstname | /booking/1 | 400            | Firstname should not be blank       | errors_array_schema.json  |
      | updateLastnameTooShort        | /booking/1 | 400            | size must be between 3 and 30       | errors_array_schema.json  |
      | updateLastnameTooLong         | /booking/1 | 400            | size must be between 3 and 30       | errors_array_schema.json  |
      | updatePhoneInvalidShort       | /booking/1 | 400            | size must be between 11 and 21      | errors_array_schema.json  |
      | updatePhoneInvalidLong        | /booking/1 | 400            | size must be between 11 and 21      | errors_array_schema.json  |
      | updateBookingInvalidEmail     | /booking/1 | 400            | must be a well-formed email address | errors_array_schema.json  |
      | updateBookingWithoutEmail     | /booking/1 | 400            | Failed to update booking            | errors_array_schema.json  |
      | updateBookingInvalidDates     | /booking/1 | 409            | Failed to update booking            | error_generic_schema.json |

