@booking @requiresAuth
Feature: Get Booking API

  @getBooking @positive
  Scenario: Get booking details by valid ID

    When user send a GET request to "<endpoint>"
    Then user get response booking status code should be <expectedStatus>
    And validate get response should contain booking details

    Examples:
      | endpoint   | expectedStatus |
      | /booking/1 | 200            |

  @getBooking @Negative
  Scenario Outline: Get booking with invalid ID
    When user send a GET request to "<endpoint>"
    Then user get response booking status code should be <expectedStatus>
    And validate error message should contain "<expectedError>"

    Examples:
      | endpoint         | expectedStatus | expectedError                     |
      | /booking/9999    | 404            | Failed to fetch booking: 404      |
      | /booking/INVALID | 404            | Not Found                         |
      | /bookings        | 404            | 404: This page could not be found |

  @getBooking @Negative
  Scenario Outline: Get booking UnAuthorized
    When user send a GET request to "<endpoint>" without token
    Then user get response booking status code should be <expectedStatus>
    And validate error message should contain "<expectedError>"

    Examples:
      | endpoint   | expectedStatus | expectedError           |
      | /booking/1 | 401            | Authentication required |

  @getBooking @Negative
  Scenario Outline: Get booking Invalid Token
    When user send a GET request invalid token to "<endpoint>"
    Then user get response booking status code should be <expectedStatus>
    And validate error message should contain "<expectedError>"

    Examples:
      | endpoint   | expectedStatus | expectedError                |
      | /booking/1 | 403            | Failed to fetch booking: 403 |