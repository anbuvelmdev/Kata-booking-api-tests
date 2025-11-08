@auth
Feature: Authentication API

  @positive
  Scenario Outline: Valid Login scenarios
    Given login using username "<username>" and password "<password>"
    Then user should receive auth status code <expectedStatus>
    And validate auth token response based on "<validationType>"

    Examples:
      | username | password | expectedStatus | validationType |
      | admin    | password | 200            | token          |

  @Negative
  Scenario Outline: Invalid Login scenarios
    Given login using username "<username>" and password "<password>"
    Then user should receive auth status code <expectedStatus>
    And validate auth token response should contain error "<expectedError>"

    Examples:
      | username      | password    | expectedStatus | expectedError       |
      | admin         | invalid     | 401            | Invalid credentials |
      |               |             | 401            | Invalid credentials |
      | admin         |             | 401            | Invalid credentials |
      |               | password    | 401            | Invalid credentials |
      | user          | password    | 401            | Invalid credentials |
      | admin<script> | password    | 401            | Invalid credentials |
      | admin         | pass@123    | 401            | Invalid credentials |
      | ' OR '1'='1   | password    | 401            | Invalid credentials |
      | admin         | ' OR '1'='1 | 401            | Invalid credentials |
