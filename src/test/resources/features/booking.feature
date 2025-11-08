@booking @requiresAuth
Feature: Hotel Booking API

  @createBooking @positive
  Scenario Outline: Create booking with valid data
    Given user loads booking data from "<testDataKey>"
    When user sends a POST request to "<endpoint>" with booking details
    Then user should receive booking status code <expectedStatus>
    And validate response based on "<validationType>"

    Examples:
      | testDataKey  | endpoint | expectedStatus | validationType |
      | validBooking | /booking | 201            | bookingid      |


  @createBooking @Negative
  Scenario Outline: Create booking with invalid or missing data
    Given user loads booking data from "<testDataKey>"
    When user sends a POST request to "<endpoint>" with booking details
    Then user should receive booking status code <expectedStatus>
    And validate response should contain error "<expectedError>"

    Examples:
      | testDataKey             | endpoint | expectedStatus | expectedError                       |
      | firstnameTooShort       | /booking | 400            | size must be between 3 and 18       |
      | firstnameTooLong        | /booking | 400            | size must be between 3 and 18       |
      | bookingMissingFirstname | /booking | 400            | Firstname should not be blank       |
      | lastnameTooShort        | /booking | 400            | size must be between 3 and 30       |
      | lastnameTooLong         | /booking | 400            | size must be between 3 and 30       |
      | phoneInvalidShort       | /booking | 400            | size must be between 11 and 21      |
      | phoneInvalidLong        | /booking | 400            | size must be between 11 and 21      |
      | bookingInvalidEmail     | /booking | 400            | must be a well-formed email address |
      | bookingInvalidDates     | /booking | 409            | Failed to create booking            |