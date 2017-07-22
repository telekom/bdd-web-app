Narrative:
Als Anwender
möchte ich mich mit einem bereits vorhandenen Accout einloggen,
um Reservierungen von Sammeltaxis vornehmen zu können.

Scenario: Einloggen
Given ist ein registrierter Anwender testnutzer
And die geöffnete Loginseite
When der Nutzer testnutzer sich einloggt
Then öffnet sich die Reservierungsseite