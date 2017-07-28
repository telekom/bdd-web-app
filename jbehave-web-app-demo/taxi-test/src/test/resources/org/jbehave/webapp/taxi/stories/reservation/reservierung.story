Erzählung:
Als eingeloggter Anwender
möchte ich Reservierungen von Sammeltaxis für bestimmte Strecken in bestimmten Zeiträumen vornehmen können
um etwaige Rabatte nutzen zu können.

Szenario: Reservierung für angebenen Zeitraum nicht möglich, da keine Angebote vorhanden sind
Gegeben ein eingeloggter Nutzer
Und ist eine Reservierung zwischen 10:00 und 10:30 Uhr
Wenn ein Sammeltaxi reserviert wird
Dann ist die Reservierung nicht erfolgreich

Szenario: Reservierung einer Sammeltaxifahrt
Gegeben ist eine Reservierung zwischen 10:00 und 10:30 Uhr
Und zwischen 10:00 Uhr und 10:15 Uhr ist der Preis 30,50 € bei 0 Mitfahrern
Und zwischen 10:15 Uhr und 10:30 Uhr ist der Preis 25,40 € bei 1 Mitfahrern
Wenn ein Sammeltaxi reserviert wird
Dann ist die Reservierung erfolgreich
Und zwischen 10:00 und 10:15 Uhr beträgt der Preis 30,50 € bei 0 Mitfahrern
Und zwischen 10:15 und 10:30 Uhr beträgt der Preis 25,40 € bei 1 Mitfahrern

Szenario: Aktualisierte Preise für bereits getätigte Reservierung anschauen
Gegeben ist eine Reservierung zwischen 10:00 und 10:30 Uhr
Und zwischen 10:00 Uhr und 10:15 Uhr ist der Preis 30,50 € bei 0 Mitfahrern
Und zwischen 10:15 Uhr und 10:30 Uhr ist der Preis 22,50 € bei 2 Mitfahrern
Wenn der Nutzer die Reservierungsseite öffnet
Dann ist die Reservierung erfolgreich
Und zwischen 10:00 und 10:15 Uhr beträgt der Preis 30,50 € bei 0 Mitfahrern
Und zwischen 10:15 und 10:30 Uhr beträgt der Preis 22,50 € bei 2 Mitfahrern
