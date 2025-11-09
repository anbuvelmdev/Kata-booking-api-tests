@e2e @booking @schema @requiresAuth
Feature: End-to-End Booking API Tests
  This feature validates the complete booking lifecycle - Create, Retrieve, Update, and Delete.

  @positive @create
  Scenario Outline: e2e booking successfully
    When create a new booking with the following details:
      | firstname          | lastname          | depositpaid          | email          | phone          | checkin          | checkout          |
      | <create_firstname> | <create_lastname> | <create_depositpaid> | <create_email> | <create_phone> | <create_checkin> | <create_checkout> |
    Then user should receive status code 201
    And validate response should contain booking details
    And response should match schema "booking_valid_response_schema.json"
    And store the booking ID for later use
    When retrieve the booking by ID
    Then user should receive status code 200
    And validate response should contain booking details
    And response should match schema "get_booking_response_schema.json"
    When update the booking with:
      | firstname          | lastname          | depositpaid          | email          | phone          | checkin          | checkout          |
      | <update_firstname> | <update_lastname> | <update_depositpaid> | <update_email> | <update_phone> | <update_checkin> | <update_checkout> |
    Then user should receive status code 200
    And validate response based on "success"
    And response should match schema "success_booking_response_schema.json"
    When delete the booking by ID
    Then user should receive status code 200
    And validate response based on "success"
    And verify the booking no longer exists
    When delete the booking by ID
    Then user should receive status code 500
    And validate response should contain error "Failed to delete booking"

    Examples:
      | create_firstname | create_lastname | create_depositpaid | create_email         | create_phone | create_checkin | create_checkout | update_firstname | update_lastname | update_depositpaid | update_email           | update_phone | update_checkin | update_checkout |
      | John             | Doe             | true               | john.doe@example.com | 12345678901  | 2025-12-01     | 2025-12-05      | John             | Wayne           | true               | john.wayne@example.com | 12345678902  | 2025-12-01     | 2025-12-05      |