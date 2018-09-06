Narrative:
As a user
I would like to log in with an existing account,
to make reservations for collective taxis.

Scenario: Redirect to the login page if the session is not present
When the user opens the login page
Then the login page is shown

Scenario: Input of invalid log data
Given invalid log in data for user
When the user logs in
Then the login page is shown
And the user receives the message that the login data is invalid

Scenario: Successful login
Given registered user
And the opened login page
When the user logs in
Then the reservation page is shown

Scenario: Redirect to the log-in with existing session
When the user opens the login page
Then the reservation page is shown
