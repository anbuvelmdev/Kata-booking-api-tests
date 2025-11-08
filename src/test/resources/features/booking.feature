# ===============================
# Positive Booking Scenarios
# ===============================
@booking @noauth @positive
Feature: Hotel Booking API - Positive Scenarios

  @createBooking
  Scenario Outline: Create booking with valid data
    Given user loads booking data from "<dataFile>"
    When user sends a POST request to "<endpoint>" with booking details
    Then user should receive booking status code <expectedStatus>
    And validate response based on "<validationType>"

    Examples:
      | dataFile          | endpoint | expectedStatus | validationType |
      | booking_data.json | /booking | 201            | validBooking   |

  @getBooking
  Scenario: Get booking details by valid ID
    When I send a GET request to "/booking/1"
    Then user response get booking status code should be 200
    And the response should contain booking details with valid fields

  @updateBooking
  Scenario: Update booking with valid ID
    Given user loads required booking data from "<dataFile>"
    When user sends a PUT request to "<endpoint>" with booking details
    Then user response update booking status code should be <expectedStatus>
    And response should contain updated booking details

    Examples:
      | dataFile                 | endpoint    | expectedStatus |
      | booking_update_data.json | /booking/75 | 200            |

  @deleteBooking
  Scenario Outline: Delete booking by valid ID
    Given a booking ID "<id>"
    When user sends DELETE request to "<endpoint>"
    Then user response delete status code should be <expectedStatus>
    And the booking should be removed successfully

    Examples:
      | id | endpoint  | expectedStatus |
      | 75 | /booking/ | 201            |
