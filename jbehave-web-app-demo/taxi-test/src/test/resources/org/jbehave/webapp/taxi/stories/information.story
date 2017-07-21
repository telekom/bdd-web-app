Narrative:
Als eingeloggter Anwender, der bereits eine Reservierung vorgenommen hat,
möchte ich auf der Reservierungsseite Informationen über aktuelle Preise erhalten,
damit ich diese kostenpflichtig buchen kann.

Scenario: Aktuelle Preise für Reservierung einstellen
Given ist das Datum 10.10.2017
And ist der Startort OrtA
And ist der Zielort OrtB
And ist der früheste Startzeitpunkt 10:00 Uhr
And ist der späteste Startzeitpunkt 12:00 Uhr
And zwischen 10:00 Uhr und 11:00 Uhr ist der Preis 15,50 €
And zwischen 11:00 Uhr und 12:00 Uhr ist der Preis 13,50 €
When die Reservierung im Simulator hinterlegt wird
Then gibt der Simulator eine Erfolgsmeldung zurück

Scenario: Reservierung einer Sammeltaxifahrt
Given ein eingeloggter Kunde kunde
And ist das Datum 10.10.2017
And ist der Startort OrtA
And ist der Zielort OrtB
And ist der früheste Startzeitpunkt 10:00 Uhr
And ist der späteste Startzeitpunkt 12:00 Uhr
When ein Sammeltaxi reserviert wird
Then ist die Reservierung erfolgreich
And zwischen 10:00 und 11:00 Uhr beträgt der Preis 15,50 €
And zwischen 11:00 und 12:00 Uhr beträgt der Preis 13,50 €

Scenario: Preise aktualisieren
When die Reservierung im Simulator gelöscht wird
Then gibt der Simulator eine Erfolgsmeldung zurück
Given ist das Datum 10.10.2017
And ist der Startort OrtA
And ist der Zielort OrtB
And ist der früheste Startzeitpunkt 10:00 Uhr
And ist der späteste Startzeitpunkt 12:00 Uhr
And zwischen 10:00 Uhr und 11:00 Uhr ist der Preis 12,50 €
And zwischen 11:00 Uhr und 12:00 Uhr ist der Preis 13,50 €
When die Reservierung im Simulator hinterlegt wird
Then gibt der Simulator eine Erfolgsmeldung zurück

Scenario: Ausloggen und Startseite öffnen
Given ein ausgeloggter Kunde
When der Nutzer die Startseite öffnet
Then dann ist die Loginseite geöffnet

Scenario: Einloggen und Aktualisierte Preise anschauen
When der Anwender kunde sich einloggt
Then öffnet sich die Reservierungsseite
And ist die Reservierung erfolgreich
And zwischen 10:00 und 11:00 Uhr beträgt der Preis 12,50 €
And zwischen 11:00 und 12:00 Uhr beträgt der Preis 13,50 €