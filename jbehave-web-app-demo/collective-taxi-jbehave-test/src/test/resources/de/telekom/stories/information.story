Meta:

Narrative:
Als eingeloggter Anwender, der bereits eine Reservierung vorgenommen hat,
möchte ich auf der Reservierungsseite Informationen über aktuelle Preise erhalten,
damit ich diese kostenpflichtig buchen kann.

Scenario: Informationen abrufen
Given ein Kunde der bereits eine Reservierung zwischen 10:00 und 11:00 Uhr vorgenommen hat
And ein eingeloggter Kunde b@kunde.de
And als Startort ist OrtA angegeben
And als Zielort ist OrtB angegeben
When ein Sammeltaxi zwischen 10:00 und 12:00 Uhr reserviert wird
Then ist die Reservierung erfolgreich
And der Preis beträgt 12,00 € zwischen 10:00 und 11:00 Uhr
And der Preis beträgt 15,50 € zwischen 11:00 und 12:00 Uhr
