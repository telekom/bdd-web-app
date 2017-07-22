Narrative:
Als Anwender
möchte ich mich mit einem bereits vorhandenen Accout einloggen,
um Reservierungen von Sammeltaxis vornehmen zu können.

Scenario: Startseite öffnen
Given ist ein registrierter Anwender benutzer
When der Nutzer die Startseite öffnet
Then ist die Loginseite geöffnet

Scenario: Einloggen
When der Anwender benutzer sich einloggt
Then öffnet sich die Reservierungsseite