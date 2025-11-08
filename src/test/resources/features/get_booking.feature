@booking @requiresAuth
Feature: Get Booking API

  @getBooking @positive
  Scenario: Get booking details by valid ID

    When user send a GET request to "<endpoint>"
    Then user get response booking status code should be <expectedStatus>
    And validate get response should contain booking details
    And response should match schema "<schemaFile>"

    Examples:
      | endpoint   | expectedStatus | schemaFile                       |
      | /booking/1 | 200            | get_booking_response_schema.json |

  @getBooking @Negative
  Scenario Outline: Get booking with invalid ID
    When user send a GET request to "<endpoint>"
    Then user get response booking status code should be <expectedStatus>
    And validate error message should contain "<expectedError>"
    And response should match schema "<schemaFile>"

    Examples:
      | endpoint         | expectedStatus | expectedError                     | schemaFile                |
      | /booking/9999    | 404            | Failed to fetch booking: 404      | error_generic_schema.json |
      | /booking/INVALID | 404            | Not Found                         | error_generic_schema.json |
      | /bookings        | 404            | 404: This page could not be found | error_generic_schema.json |

  @getBooking @Negative
  Scenario Outline: Get booking UnAuthorized
    When user send a GET request to "<endpoint>" without token
    Then user get response booking status code should be <expectedStatus>
    And validate error message should contain "<expectedError>"
    And response should match schema "<schemaFile>"

    Examples:
      | endpoint   | expectedStatus | expectedError           | schemaFile                |
      | /booking/1 | 401            | Authentication required | error_generic_schema.json |

  @getBooking @Negative
  Scenario Outline: Get booking Invalid Token
    When user send a GET request invalid token to "<endpoint>"
    Then user get response booking status code should be <expectedStatus>
    And validate error message should contain "<expectedError>"
    And response should match schema "<schemaFile>"

    Examples:
      | endpoint   | expectedStatus | expectedError                | schemaFile                |
      | /booking/1 | 403            | Failed to fetch booking: 403 | error_generic_schema.json |