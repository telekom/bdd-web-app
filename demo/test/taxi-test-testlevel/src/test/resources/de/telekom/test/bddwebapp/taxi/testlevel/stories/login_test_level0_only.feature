@TestLevel(testLevels = 0)
Feature:
  As a framwork user
  I would like to write test steps for test level 0 only
  to implement the same steps for other test levels different

  Scenario: Successful login
    Given registered user as user
    And the opened login page
    When the user logs in with $user.username $user.password
    Then the reservation page is shown
