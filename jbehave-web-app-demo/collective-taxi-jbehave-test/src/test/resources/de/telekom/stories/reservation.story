Meta:

Narrative:
Als eingeloggter Anwender
möchte ich Reservierungen von Sammeltaxis für bestimmte Strecken in bestimmten Zeiträumen vornehmen können
um etwaige Rabatte nutzen zu können.

Scenario: Reservierung einer Sammeltaxifahrt
Given ein eingeloggter Kunde a@kunde.de
And als Startort ist OrtA angegeben
And als Zielort ist OrtB angegeben
When ein Sammeltaxi zwischen 10:00 und 12:00 Uhr reserviert wird
Then ist die Reservierung erfolgreich
And der Preis beträgt 15,50 € zwischen 10:00 und 12:00 Uhr
