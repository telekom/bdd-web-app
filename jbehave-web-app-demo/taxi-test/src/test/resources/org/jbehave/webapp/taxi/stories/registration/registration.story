Narrative:
Als Anwender
möchte ich mich registrieren können,
um Reservierungen von Sammeltaxis vornehmen zu können.

Scenario: Registrierung
Given die geöffnete Registrierungsseite
And valide Registrierungsdaten für Nutzer testnutzer
When der Nutzer testnutzer die Registrierung durchführt
Then ist die Loginseite geöffnet
And der Nutzer erhält die Nachricht, dass er registriert ist
