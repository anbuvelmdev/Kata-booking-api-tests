@booking @noauth
Feature: Hotel Booking API

  @createBooking
  Scenario Outline: Create booking with different data and expected outcomes
    Given user loads booking data from "<dataFile>"
    When user sends a POST request to "<endpoint>" with booking details
    Then user should receive status code <expectedStatus>
    And validate response based on "<validationType>"

    Examples:
      | dataFile                                 | endpoint  | expectedStatus | validationType   |
      | booking_data.json                        | /booking  | 201            | validBooking     |
      | booking_missing_firstname.json           | /booking  | 400            | missingFirstname |
      | booking_invalid_email.json               | /booking  | 400            | invalidEmail     |
      | booking_invalid_dates.json               | /booking  | 400            | invalidDates     |

