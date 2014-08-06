Feature: Testing AWAC Integration

  Scenario: AWAC Integration
    Given I have created new account
    When I have search new account
    Then The account should be "gho"
    And  Perform delete of the account
    And Perform delete of the organization