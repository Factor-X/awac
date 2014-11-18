@TestingExample
Feature: Testing Cucumber Integration

  Scenario: Cucumber Integration
    Given I have setup Play
    When I go to the landing page
    Then the title should be "AWAC"
    #Then the title should be "Application is starting... Please try again in a moment."
