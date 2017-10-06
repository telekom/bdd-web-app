Erzählung:
Als Anwender
möchte ich mich mit einem bereits vorhandenen Account einloggen,
um Reservierungen von Sammeltaxis vornehmen zu können.

Szenario: Umleitung auf die Loginseite bei nicht vorhandener Session
Wenn der Nutzer die Startseite öffnet
Dann wird die Loginseite angezeigt

Szenario: Eingabe von ungültigen Logindaten
Gegeben ungültige Logindaten
Wenn der Nutzer sich einloggt
Dann wird die Loginseite angezeigt
Und der Nutzer erhält die Nachricht, dass die Logindaten ungültig sind

Szenario: Erfolgreiches einloggen
Gegeben ein registrierter Nutzer
Und die geöffnete Loginseite
Wenn der Nutzer sich einloggt
Dann wird die Reservierungsseite angezeigt

Szenario: Umleitung auf die Loginseite bei vorhandener Session
Wenn der Nutzer die Startseite öffnet
Dann wird die Reservierungsseite angezeigt
