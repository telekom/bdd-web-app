Narrative:
Als Entwickler
möchte ich ein aussagekräftiges Reporting bei Fehlern im Kontrollfluss haben,
um die Fehler schnell beheben zu können.

Scenario: Registrierung
Given die geöffnete Registrierungsseite
And valide Registrierungsdaten mit Kontrollflussfehler für Nutzer nutzer
When der Nutzer testnutzer die Registrierung durchführt
Then ist die Loginseite geöffnet
And der Nutzer erhält die Nachricht, dass er registriert ist
