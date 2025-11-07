@booking @deleteapi
Feature: Delete Booking API

  Background:
    Given login using username "admin" and password "password"

  @positive
  Scenario: Delete an existing booking by ID
    Given a booking ID "45"
    When user sends DELETE request to "/booking/{id}"
    Then the response delete status code should be 201
    And the booking should be removed successfully
