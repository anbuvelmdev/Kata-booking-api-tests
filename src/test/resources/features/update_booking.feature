@booking @requiresAuth
Feature: Update Booking API

  @updateBooking @positive
  Scenario: Update booking with valid ID
    Given user loads required booking data from "<testDataKey>"
    When user sends a PUT request to "<endpoint>" with booking details
    Then user update response booking status code should be <expectedStatus>
    And validate update booking response based on "<validationType>"

    Examples:
      | testDataKey   | endpoint   | expectedStatus | validationType |
      | updateBooking | /booking/2 | 200            | success        |

  @updateBooking @Negative
  Scenario Outline: Update booking with invalid data
    Given user loads required booking data from "<testDataKey>"
    When user sends a PUT request to "<endpoint>" with booking details
    Then user update response booking status code should be <expectedStatus>
    And validate update booking response should contain error "<expectedError>"

    Examples:
      | testDataKey                   | endpoint   | expectedStatus | expectedError                       |
      | updateBookingInvalidEmail     | /booking/2 | 400            | must be a well-formed email address |
      | updateBookingMissingFirstname | /booking/2 | 400            | Firstname should not be blank       |

