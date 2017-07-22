Narrative:
Als Betreiber
möchte ich einen Schutz vor Falscheingaben haben,
um keine inkonsistenten Daten zu haben.

Scenario: Registrierung
Given die geöffnete Registrierungsseite
And invalide Registrierungsdaten für Nutzer testnutzer
When der Nutzer testnutzer die Registrierung durchführt
Then ist die Registrierungsseite geöffnet
