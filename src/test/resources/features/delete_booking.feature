@booking @requiresAuth
Feature: Delete Booking API

  @deleteBooking @positive
  Scenario Outline: Delete booking by valid ID
    Given create a new booking with the following details:
      | firstname | lastname | depositpaid | email             | phone       | checkin    | checkout   |
      | John      | Doe      | true        | john.doe@test.com | 98765432101 | 2025-11-01 | 2025-11-05 |
    And store the booking ID for later use
    When user sends DELETE request to "<endpoint>"
    Then user should receive status code <expectedStatus>
    And the booking should be removed successfully and contain "<validationType>"
    And response should match schema "<schemaFile>"

    Examples:
      | endpoint  | expectedStatus | validationType | schemaFile                           |
      | /booking/ | 200            | success        | success_booking_response_schema.json |

  @deleteBooking @Negative
  Scenario Outline: Delete booking by Invalid ID
    Given a booking ID "<id>"
    When user sends DELETE request to "<endpoint>"
    Then user should receive status code <expectedStatus>
    And validate response should contain error "<expectedError>"
    And response should match schema "<schemaFile>"

    Examples:
      | id      | endpoint  | expectedStatus | expectedError            | schemaFile                |
      | 9999    | /booking/ | 500            | Failed to delete booking | error_generic_schema.json |
      | INVALID | /booking/ | 500            | Failed to delete booking | error_generic_schema.json |

  @deleteBooking @Negative
  Scenario Outline: Delete booking UnAuthorized
    Given a booking ID "<id>"
    When user sends DELETE request unauthorized to "<endpoint>"
    Then user should receive status code <expectedStatus>
    And validate response should contain error "<expectedError>"
    And response should match schema "<schemaFile>"

    Examples:
      | id | endpoint  | expectedStatus | expectedError           | schemaFile                |
      | 2  | /booking/ | 401            | Authentication required | error_generic_schema.json |

  @getBooking @Negative
  Scenario Outline: Delete booking Invalid Token
    Given a booking ID "<id>"
    When user send a DELETE request invalid token to "<endpoint>"
    Then user should receive status code <expectedStatus>
    And validate response should contain error "<expectedError>"
    And response should match schema "<schemaFile>"

    Examples:
      | id | endpoint  | expectedStatus | expectedError            | schemaFile                |
      | 2  | /booking/ | 500            | Failed to delete booking | error_generic_schema.json |
