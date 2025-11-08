# ===============================
# Negative Booking Scenarios
# ===============================
@booking @noauth @negative
Feature: Hotel Booking API - Negative Scenarios

  @createBookingNegative
  Scenario Outline: Create booking with invalid or missing data
    Given user loads booking data from "<dataFile>"
    When user sends a POST request to "<endpoint>" with booking details
    Then user should receive booking status code <expectedStatus>
    And response should contain error "<expectedError>"

    Examples:
      | dataFile                         | endpoint | expectedStatus | expectedError     |
      | booking_without_email_phone.json | /booking | 400            | missingemailphone |
      | booking_missing_firstname.json   | /booking | 400            | missingFirstname  |
      | booking_invalid_email.json       | /booking | 400            | invalidEmail      |
      | booking_invalid_dates.json       | /booking | 400            | invalidDates      |

  @getBookingNegative
  Scenario: Get booking with invalid ID
    When I send a GET request to "/booking/999999"
    Then user response get booking status code should be 404
    And the error message should contain "Not Found"

  @updateBookingNegative
  Scenario Outline: Update booking with invalid data
    Given user loads required booking data from "<dataFile>"
    When user sends a PUT request to "<endpoint>" with booking details
    Then user response update booking status code should be <expectedStatus>
    And response should contain error "<expectedError>"

    Examples:
      | dataFile                       | endpoint   | expectedStatus | expectedError     |
      | booking_invalid_email.json     | /booking/1 | 400            | Invalid email     |
      | booking_missing_firstname.json | /booking/1 | 400            | Missing firstname |

  @deleteBooking
  Scenario Outline: Delete booking by Invalid ID
    Given a booking ID "<id>"
    When user sends DELETE request to "<endpoint>"
    Then user response delete status code should be <expectedStatus>
    And response should contain error "Booking not found"

    Examples:
      | id   | endpoint  | expectedStatus | expectedStatus    |
      | 9999 | /booking/ | 404            | Booking not found |

