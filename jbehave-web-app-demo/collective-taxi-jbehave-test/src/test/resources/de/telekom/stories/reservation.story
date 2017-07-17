Meta:

Narrative:
Als eingeloggter Anwender
möchte ich Reservierungen von Sammeltaxis für bestimmte Strecken in bestimmten Zeiträumen vornehmen können
um etwaige Rabatte nutzen zu können.

Scenario: Mögliche Reservierimg vorbereiten
Given ist der Startort OrtA
And ist der Zielort OrtB
And ist der früheste Startzeitpunkt 10:00 Uhr
And ist der späteste Startzeitpunkt 12:00 Uhr
When die Reservierung im Simulator hinterlegt wird
Then gibt der Simulator eine Erfolgsmeldung zurück

Scenario: Reservierung einer Sammeltaxifahrt
Given ein eingeloggter Kunde kunde
And ist der Startort OrtA
And ist der Zielort OrtB
And ist der früheste Startzeitpunkt 10:00 Uhr
And ist der späteste Startzeitpunkt 12:00 Uhr
When ein Sammeltaxi reserviert wird
Then ist die Reservierung erfolgreich
And der Preis beträgt 15,50 € zwischen 10:00 und 12:00 Uhr
