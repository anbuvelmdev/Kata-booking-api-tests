@booking
Feature: Hotel Booking API

  @positive
  Scenario: Create booking without auth token
    Given user have valid booking data
    When user send a POST request to "/booking" with booking details
    Then user should receive status code 201
    And the response should contain the booking id
