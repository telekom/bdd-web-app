Narrative:
Als eingeloggter Anwender, der bereits eine Reservierung vorgenommen hat,
möchte ich auf der Reservierungsseite Informationen über aktuelle Preise erhalten,
damit ich diese kostenpflichtig buchen kann.

Scenario: Initialen Preise für Reservierung einstellen
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

Scenario: Preise aktualisieren
Given ist eine valide Reservierung zwischen 10:00 und 12:00 Uhr
And zwischen 10:00 Uhr und 11:00 Uhr ist der Preis 30,50 €
And zwischen 11:00 Uhr und 12:00 Uhr ist der Preis 22,50 €
When die Reservierung im Simulator aktualisiert wird
Then gibt der Simulator eine Erfolgsmeldung zurück

Scenario: Aktualisierte Preise anschauen
When der Nutzer die Reservierungsseite öffnet
Then ist die Reservierung erfolgreich
And zwischen 10:00 und 11:00 Uhr beträgt der Preis 30,50 €
And zwischen 11:00 und 12:00 Uhr beträgt der Preis 22,50 €