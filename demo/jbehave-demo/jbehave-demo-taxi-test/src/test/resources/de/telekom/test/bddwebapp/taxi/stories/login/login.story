Narrative:
As a user
I would like to log in with an existing account,
to make reservations for collective taxis.

Scenario: Successful login
Given registered user as user
And the opened login page
When the user logs in with $user.username $user.password
Then the reservation page is shown
