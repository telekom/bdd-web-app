Erzählung:
Als eingeloggter Anwender
möchte ich Reservierungen von Sammeltaxis für bestimmte Strecken in bestimmten Zeiträumen vornehmen können
um etwaige Rabatte nutzen zu können.

Szenario: Reservierung für angegebenen Zeitraum nicht möglich, da keine Angebote vorhanden sind
Gegeben ein eingeloggter Nutzer
Und eine valide Reservierung
Wenn ein Sammeltaxi reserviert wird
Dann ist die Reservierung nicht erfolgreich

Szenario: Reservierung einer Sammeltaxifahrt
Gegeben eine valide Reservierung
Und zwischen 10:00 Uhr und 10:30 Uhr beträgt der Preis 30,50 € bei 0 Mitfahrern
Wenn ein Sammeltaxi reserviert wird
Dann ist die Reservierung erfolgreich
Und zwischen 10:00 und 10:30 Uhr beträgt der Preis 30,50 € bei 0 Mitfahrern

Szenario: Aktualisierte Preise für bereits getätigte Reservierung anschauen
Gegeben die bereits getätigte Reservierung
Und zwischen 10:00 Uhr und 10:15 Uhr beträgt der Preis 30,50 € bei 0 Mitfahrern
Und zwischen 10:15 Uhr und 10:30 Uhr beträgt der Preis 15,50 € bei 2 Mitfahrern
Wenn der Nutzer die Reservierungsseite öffnet
Dann ist die Reservierung erfolgreich
Und zwischen 10:00 und 10:15 Uhr beträgt der Preis 30,50 € bei 0 Mitfahrern
Und zwischen 10:15 und 10:30 Uhr beträgt der Preis 15,50 € bei 2 Mitfahrern
