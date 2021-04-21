@ignore
Feature: Ignore me
  As a framework user
  I expect that everything works with JUnit defaults
  like the @ignore Annotation for features

  Scenario: This scenario should not executed because the feature is ignored
    Then this step fail
