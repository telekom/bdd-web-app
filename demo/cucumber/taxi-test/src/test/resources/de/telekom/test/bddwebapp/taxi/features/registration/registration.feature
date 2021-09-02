Feature: Registration
  As a user
  I would like to register,
  to make reservations for collective taxis.

  Scenario: Open the registration page from the homepage
    Given the opened login page
    When user clicks the link to the registration
    Then the registration page is shown

  Scenario: Entering invalid registration data
    Given the openend registration page
    When the user register with
      | firstName | lastName | userName                  | password |
      | Hans      | Müller   | userName+$RANDOM+@test.de |          |
      | Hans      | Müller   |                           | password |
    Then the registration page is shown
    And the user receives the message that the registration data is invalid

  Scenario: Successful registration
    Given the openend registration page
    When the user register with
      | firstName | lastName | userName                  | password |
      | Hans      | Müller   | userName+$RANDOM+@test.de | password |
    Then the login page is shown
    And the user receives the registered message
