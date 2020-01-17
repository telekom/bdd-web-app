Narrative:
As a user
I would like to log in with an existing account,
to make reservations for collective taxis.

Scenario: Redirect to the login page if the session is not present
When the user opens the login page
Then the login page is shown

Scenario: Input of invalid log data
When the user logs in with invalid@user.de password
Then the login page is shown
And the user receives the message that the login data is invalid