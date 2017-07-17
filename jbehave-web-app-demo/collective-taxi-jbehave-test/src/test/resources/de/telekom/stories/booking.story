Meta:

Narrative:
Als eingeloggter Anwender, der bereits eine Reservierung vorgenommen hat,
möchte ich für bestimmte Zeiteinheiten meines angegebenen Zeitraumes Buchungen vornehmen,
damit ich das Taxi zu dem angegebenen Preis in Anspruch nehmen kann.

Scenario: Information TODO information Story einbinden
Given ein Kunde der bereits eine Reservierung zwischen 10:00 und 11:00 Uhr vorgenommen hat
And ein eingeloggter Kunde b@kunde.de
And ist der Startort OrtA
And ist der Zielort OrtB
And ist der früheste Startzeitpunkt 10:00 Uhr
And ist der späteste Startzeitpunkt 12:00 Uhr
When ein Sammeltaxi reserviert wird
Then ist die Reservierung erfolgreich
And der Preis beträgt 12,00 € zwischen 10:00 und 11:00 Uhr
And der Preis beträgt 15,50 € zwischen 11:00 und 12:00 Uhr

Scenario: Buchung
When die Reservierung zwischen 10:00 und 11:00 gebucht wird
Then erhält der Kunde eine Buchungsbestätigung
