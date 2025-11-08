@booking @updateapi @requiresAuth
Feature: Update Booking API

#  Background:
#    Given login using username "admin" and password "password"

  @positive
  Scenario: Update an existing booking by ID
    Given booking with id "75" exists
    When user updates booking "75" with new details
    Then the response update booking status code should be 200
    And response should contain updated booking details
