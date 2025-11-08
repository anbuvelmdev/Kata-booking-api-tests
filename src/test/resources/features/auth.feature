@auth
Feature: Authentication API

  Scenario Outline: Login scenarios
    Given login using username "<username>" and password "<password>"
    Then the response auth login status code should <expectedStatus>
    And the response should contain a token or error "<expectedError>"

    Examples:
      | username      | password    | expectedStatus | expectedError       |
      | admin         | password    | 200            |                     |
      | admin         | invalid     | 401            | Invalid credentials |
      |               |             | 401            | Invalid credentials |
      | admin         |             | 401            | Invalid credentials |
      |               | password    | 401            | Invalid credentials |
      | user          | password    | 401            | Invalid credentials |
      | admin<script> | password    | 401            | Invalid credentials |
      | admin         | pass@123    | 401            | Invalid credentials |
      | ' OR '1'='1   | password    | 401            | Invalid credentials |
      | admin         | ' OR '1'='1 | 401            | Invalid credentials |
