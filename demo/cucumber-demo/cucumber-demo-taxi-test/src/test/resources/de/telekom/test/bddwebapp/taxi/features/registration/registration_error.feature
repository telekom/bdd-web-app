@ignore
Feature: Registration Error
  As a developer
  I would like to have a meaningful reporting for errors in the control flow,
  in order to fix the errors quickly.

  Scenario: Successful registration
    Given the opened registration page
    When the user register with
      | firstName | lastName | userName                  | password |
      | Hans      | M\u00fcller   | userName+$RANDOM+@test.de | password |
    Then the login page is shown
    And the user receives the registered message

  Scenario: Registration with application error
    Given the opened registration page
    When the user register with
      | firstName | lastName | userName      | password |
      | Hans      | M\u00fcller   | error@test.de | password |
    Then the login page is shown
    And the user receives the registered message
