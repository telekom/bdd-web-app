Feature: Login
  As a user
  I would like to log in with an existing account,
  to make reservations for collective taxis.

  Scenario: Input of invalid log data
    Given the opened login page
    When the user logs in with invalid@user.de password
    Then the login page is shown
    And the user receives the message that the login data is invalid

  Scenario: Successful login
    Given registered user as user
    And the opened login page
    When the user logs in with $user.username $user.password
    Then the reservation page is shown
    When the user opens the login page
    Then the reservation page is shown
