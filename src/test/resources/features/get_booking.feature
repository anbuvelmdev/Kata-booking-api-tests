@Booking @getapi
Feature: Retrieve Booking details

  Background:
    Given login using username "admin" and password "password"

  @positive
  Scenario: Get booking details by valid ID
    When I send a GET request to "/booking/1"
    Then the response get booking status code should be 200
    And the response should contain booking details with valid fields

  @negative
  Scenario: Get booking with invalid ID
    When I send a GET request to "/booking/999999"
    Then the response get booking status code should be 404
    And the error message should contain "Not Found"

  @negative
  Scenario: Get booking with invalid string
    When I send a GET request to "/booking/INVALID"
    Then the response get booking status code should be 403
    And the error message should contain "Method Not Allowed"

  @negative
  Scenario: Get booking without auth token
    When I send a GET request to "/booking/1" without token
    Then the response get booking status code should be 401
    And the error message should contain "Authentication required"