Narrative:
Als eingeloggter Anwender
möchte ich wissen das Reservierungen von Sammeltaxis für bestimmte Strecken in bestimmten Zeiträumen nicht möglich sind
um alternative Reservierungen vornehmen zu können

Scenario: Reservierung einer Sammeltaxifahrt
Given ist eine valide Reservierung zwischen 10:00 und 10:30 Uhr
And ein eingeloggter Nutzer testnutzer
When ein Sammeltaxi reserviert wird
Then ist die Reservierung nicht erfolgreich