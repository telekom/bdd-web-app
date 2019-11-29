Feature:
  As a framework user
  I would like to write test steps for every test level
  to reuse test steps on different environments

  Scenario: Successful login
    Given registered user as user
    And the opened login page
    When the user logs in with $user.username $user.password
    Then the reservation page is shown
