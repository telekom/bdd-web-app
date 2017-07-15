Meta:

Narrative:
Als Anwender
möchte ich mich registrieren können,
um Reservierungen von Sammeltaxis vornehmen zu können.

Scenario: Startseite aufrufen
When der Nutzer die Startseite öffnet
Then dann ist die Startseite geöffnet
And die Startseite enthält einen Link zur Registrierung

Scenario: Registrierungsseite öffnen
When der Nutzer den Link zur Registrierung anklickt
Then öffnet sich die Registrierungsseite

Scenario: Registrierung
When der Nutzer die Registrierung erfolgreich durchführt
Then ist der Anwender registriert
