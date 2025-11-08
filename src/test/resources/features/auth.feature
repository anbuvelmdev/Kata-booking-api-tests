@auth
Feature: Authentication API

  @positive
  Scenario Outline: Valid Login scenarios
    Given login using username "<username>" and password "<password>"
    Then user should receive auth status code <expectedStatus>
    And validate auth token response based on "<validationType>"
    And response should match schema "<schemaFile>"

    Examples:
      | username | password | expectedStatus | validationType | schemaFile                      |
      | admin    | password | 200            | token          | token_auth_response_schema.json |

  @Negative
  Scenario Outline: Invalid Login scenarios
    Given login using username "<username>" and password "<password>"
    Then user should receive auth status code <expectedStatus>
    And validate auth token response should contain error "<expectedError>"
    And response should match schema "<schemaFile>"

    Examples:
      | username      | password    | expectedStatus | expectedError       | schemaFile                |
      | admin         | invalid     | 401            | Invalid credentials | error_generic_schema.json |
      |               |             | 401            | Invalid credentials | error_generic_schema.json |
      | admin         |             | 401            | Invalid credentials | error_generic_schema.json |
      |               | password    | 401            | Invalid credentials | error_generic_schema.json |
      | user          | password    | 401            | Invalid credentials | error_generic_schema.json |
      | admin<script> | password    | 401            | Invalid credentials | error_generic_schema.json |
      | admin         | pass@123    | 401            | Invalid credentials | error_generic_schema.json |
      | ' OR '1'='1   | password    | 401            | Invalid credentials | error_generic_schema.json |
      | admin         | ' OR '1'='1 | 401            | Invalid credentials | error_generic_schema.json |
