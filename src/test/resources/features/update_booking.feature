@booking @requiresAuth
Feature: Update Booking API

  @updateBooking @positive
  Scenario: Update booking with valid ID
    Given user loads required booking data from "<testDataKey>"
    When user sends a PUT request to "<endpoint>" with booking details
    Then user update response booking status code should be <expectedStatus>
    And validate update booking response based on "<validationType>"

    Examples:
      | testDataKey        | endpoint   | expectedStatus | validationType |
      | updateValidBooking | /booking/2 | 200            | success        |

  @updateBooking @Negative
  Scenario Outline: Update booking with invalid data
    Given user loads required booking data from "<testDataKey>"
    When user sends a PUT request to "<endpoint>" with booking details
    Then user update response booking status code should be <expectedStatus>
    And validate update booking response should contain error "<expectedError>"

    Examples:
      | testDataKey                   | endpoint   | expectedStatus | expectedError                       |
      | updateFirstnameTooShort       | /booking/5 | 400            | size must be between 3 and 18       |
      | updateFirstnameTooLong        | /booking/5 | 400            | size must be between 3 and 18       |
      | updateBookingMissingFirstname | /booking/5 | 400            | Firstname should not be blank       |
      | updateLastnameTooShort        | /booking/5 | 400            | size must be between 3 and 30       |
      | updateLastnameTooLong         | /booking/5 | 400            | size must be between 3 and 30       |
      | updatePhoneInvalidShort       | /booking/5 | 400            | size must be between 11 and 21      |
      | updatePhoneInvalidLong        | /booking/5 | 400            | size must be between 11 and 21      |
      | updateBookingInvalidEmail     | /booking/5 | 400            | must be a well-formed email address |
      | updateBookingWithoutEmail     | /booking/5 | 400            | Failed to update booking |
      | updateBookingInvalidDates     | /booking/5 | 409            | Failed to update booking            |

