Narrative:
As a developer
I would like to have a meaningful reporting for errors in the control flow,
in order to fix the errors quickly.

Scenario: Registration with application error
Given the openend registration page
And valid registration data with error for user
When the user successfully completed the registration
Then the login page is shown
And the user receives the registered message
