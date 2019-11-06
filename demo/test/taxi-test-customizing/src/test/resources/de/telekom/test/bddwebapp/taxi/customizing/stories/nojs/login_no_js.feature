@AlternativeWebDriverConfiguration(NoJsWebDriverConfiguration.class)
Feature:
  As a framwork user
  I would like to use special web driver configurations
  to test for example the behaviour when java script is disabled

  Scenario: Successful reservation, because there are now a offer available
    Given logged in customer user
    And example reservation between 10:00 and 10:30
    And the price is 30,50 € with 0 other passengers between 10:00 and 10:30
    When reserve a shared taxi
    Then the reservation is not possible because java script is disabled