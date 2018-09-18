Narrative:
As a developer
I would like to have a meaningful reporting for errors in the control flow,
in order to fix the errors quickly.

Scenario: Registration with application error
Given the openend registration page
When the user register with
|firstName|lastName|userName|password|
|Hans|Müller|error@test.de|password|
Then the login page is shown
And the user receives the registered message
