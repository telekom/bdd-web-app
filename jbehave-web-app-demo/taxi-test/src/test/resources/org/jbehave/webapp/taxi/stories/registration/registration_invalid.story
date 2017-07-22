Narrative:
Als Betreiber
möchte ich einen Schutz vor Falscheingaben haben,
um keine inkonsistenten Daten zu haben.

Scenario: Registrierung
Given die geöffnete Registrierungsseite
And invalide Registrierungsdaten für Nutzer benutzer
When der Nutzer benutzer die Registrierung durchführt
Then ist die Registrierungsseite geöffnet
