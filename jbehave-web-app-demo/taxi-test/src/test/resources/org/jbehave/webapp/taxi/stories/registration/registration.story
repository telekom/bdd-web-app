Narrative:
Als Anwender
möchte ich mich registrieren können,
um Reservierungen von Sammeltaxis vornehmen zu können.

Scenario: Registrierung
Given die geöffnete Registrierungsseite
And valide Registrierungsdaten für Nutzer benutzer
When der Nutzer benutzer die Registrierung durchführt
Then ist die Loginseite geöffnet
And der Anwender erhält die Nachricht, dass er registriert ist
