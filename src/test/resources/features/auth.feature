@auth
Feature: Authentication API

  Scenario Outline: Login scenarios
    Given I have username "<username>" and password "<password>"
    When I send a POST request to "<authEndpoint>"
    Then the response status code should <expectedStatus>
    And the response should contain a token or error "<expectedError>"

    Examples:
      | username | password | authEndpoint   | expectedStatus | expectedError       |
      | admin    | password | /auth/login    | 200            |                     |
      | admin    | invalid  | /auth/login    | 401            | Invalid credentials |
      |          |          | /auth/login    | 401            | Invalid credentials |
      | admin    |          | /auth/login    | 401            | Invalid credentials |
      |          | password | /auth/login    | 401            | Invalid credentials |
