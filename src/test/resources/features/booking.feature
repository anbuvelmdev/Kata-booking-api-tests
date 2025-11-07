@booking
Feature: Hotel Booking API

  @positive
  Scenario Outline: Create a new hotel booking
    Given login using username "<username>" and password "<password>"
    When I send a POST request to "/booking" with booking details
    Then I should receive status code "<expectedStatus>"
    And the response should contain the booking id

    Examples:
      | username | password | expectedStatus |
      | admin    | password | 200 |