Narrative:
Als Anwender
möchte ich mich registrieren können,
um Reservierungen von Sammeltaxis vornehmen zu können.

Scenario: Startseite aufrufen
When der Nutzer die Startseite öffnet
Then dann ist die Loginseite geöffnet

Scenario: Registrierungsseite öffnen
When der Nutzer den Link zur Registrierung anklickt
Then öffnet sich die Registrierungsseite

Scenario: Registrierung
Given valide Registrierungsdaten für Nutzer benutzer
When der Nutzer benutzer die Registrierung durchführt
Then dann ist die Loginseite geöffnet
And der Anwender erhält die Nachricht, dass er registriert ist
