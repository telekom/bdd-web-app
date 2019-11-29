@ApiOnly
@TestLevel(testLevels = 2)
Feature:
  As a framework user
  I would like to write api test without frontend instrumentalisation
  to use the framework for api tests only

  Scenario: Successful login
    Given registered user as user
