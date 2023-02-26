Feature: Override login
  As a framework user
  I would like to override login steps for different environment
  to be able to reuse the test for different integration levels

  Scenario: Successful login
    Given ov registered user as user
    And the opened login page
    When the user logs in with $user.username $user.password
    Then the reservation page is shown