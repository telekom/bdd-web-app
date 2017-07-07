Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: Reservierung einer Sammeltaxifahrt für Kunde A
Given ein eingeloggter Kunde KundeA
And als Startort ist OrtA angegeben
And als Zielort ist OrtB angegeben
When ein Sammeltaxi zwischen 10:00 und 11:00 Uhr reserviert wird
Then ist die Reservierung erfolgreich
And der Preis beträgt 15,50 €

Scenario: Reservierung einer Sammeltaxifahrt für Kunde B
Given ein eingeloggter Kunde KundeB
And als Startort ist OrtA angegeben
And als Zielort ist OrtB angegeben
When ein Sammeltaxi zwischen 10:00 und 12:00 Uhr reserviert wird
Then ist die Reservierung erfolgreich
And der Preis beträgt 15,50 €
And der Preis beträgt 12,00 € zwischen 10:00 und 11:00 Uhr
