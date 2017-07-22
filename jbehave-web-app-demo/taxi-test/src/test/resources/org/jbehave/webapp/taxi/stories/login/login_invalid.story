Narrative:
Als Anwender
möchte ich mich mit einem bereits vorhandenen Accout einloggen,
um Reservierungen von Sammeltaxis vornehmen zu können.

Scenario: Einloggen
Given invalide Logindaten für Nutzer testnutzer
And die geöffnete Loginseite
When der Nutzer testnutzer sich einloggt
Then ist die Loginseite geöffnet
And der Nutzer erhält die Nachricht, dass die Logindaten ungültig sind