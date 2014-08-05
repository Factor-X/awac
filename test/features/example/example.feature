Feature: Testing Cucumber Integration

  Scenario: Cucumber Integration
    Given I have setup Play
    When I go to the landing page
    Then the title should be "eu.factorx.awac.dto.myrmex.get.ExceptionsDTO"
    #Then the title should be "Application is starting... Please try again in a moment."
