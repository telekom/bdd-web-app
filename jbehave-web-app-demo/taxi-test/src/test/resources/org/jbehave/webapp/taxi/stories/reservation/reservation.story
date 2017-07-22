Narrative:
Als eingeloggter Anwender
möchte ich Reservierungen von Sammeltaxis für bestimmte Strecken in bestimmten Zeiträumen vornehmen können
um etwaige Rabatte nutzen zu können.

Scenario: Aktuelle Preise für Reservierung einstellen
Given ist eine valide Reservierung zwischen 10:00 und 12:00 Uhr
And zwischen 10:00 Uhr und 11:00 Uhr ist der Preis 30,50 €
And zwischen 11:00 Uhr und 12:00 Uhr ist der Preis 25,40 €
When die Reservierung im Simulator hinterlegt wird
Then gibt der Simulator eine Erfolgsmeldung zurück

Scenario: Reservierung einer Sammeltaxifahrt
Given ist eine valide Reservierung zwischen 10:00 und 12:00 Uhr
And ein eingeloggter Nutzer testnutzer
When ein Sammeltaxi reserviert wird
Then ist die Reservierung erfolgreich
And zwischen 10:00 und 11:00 Uhr beträgt der Preis 30,50 €
And zwischen 11:00 und 12:00 Uhr beträgt der Preis 25,40 €