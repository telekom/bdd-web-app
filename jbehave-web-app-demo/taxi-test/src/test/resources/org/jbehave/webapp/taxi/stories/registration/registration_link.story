Narrative:
Als Anwender
möchte ich über die Startseite auf die Registrierung gelangen,
damit eine Registrierung vornehmen kann.

Scenario: Startseite aufrufen
When der Nutzer die Startseite öffnet
Then ist die Loginseite geöffnet

Scenario: Registrierungsseite öffnen
When der Nutzer den Link zur Registrierung anklickt
Then ist die Registrierungsseite geöffnet