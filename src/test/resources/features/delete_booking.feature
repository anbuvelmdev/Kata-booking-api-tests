@booking @requiresAuth
Feature: Delete Booking API

  @deleteBooking @positive
  Scenario Outline: Delete booking by valid ID
    Given a booking ID "<id>"
    When user sends DELETE request to "<endpoint>"
    Then user response delete status code should be <expectedStatus>
    And the booking should be removed successfully and contain "<validationType>"

    Examples:
      | id | endpoint  | expectedStatus | validationType |
      | 2  | /booking/ | 200            | success        |

  @deleteBooking @Negative
  Scenario Outline: Delete booking by Invalid ID
    Given a booking ID "<id>"
    When user sends DELETE request to "<endpoint>"
    Then user response delete status code should be <expectedStatus>
    And delete response should contain error "<expectedError>"

    Examples:
      | id      | endpoint  | expectedStatus | expectedError            |
      | 9999    | /booking/ | 500            | Failed to delete booking |
      | INVALID | /booking/ | 500            | Failed to delete booking |
      |         | /booking/ | 308            | /api/booking             |

  @deleteBooking @Negative
  Scenario Outline: Delete booking UnAuthorized
    Given a booking ID "<id>"
    When user sends DELETE request unauthorized to "<endpoint>"
    Then user response delete status code should be <expectedStatus>
    And delete response should contain error "<expectedError>"

    Examples:
      | id | endpoint  | expectedStatus | expectedError           |
      | 3  | /booking/ | 401            | Authentication required |

  @getBooking @Negative
  Scenario Outline: Delete booking Invalid Token
    Given a booking ID "<id>"
    When user send a DELETE request invalid token to "<endpoint>"
    Then user response delete status code should be <expectedStatus>
    And delete response should contain error "<expectedError>"

    Examples:
      | id | endpoint  | expectedStatus | expectedError                |
      | 3  | /booking/ | 403            | Failed to fetch booking: 403 |
