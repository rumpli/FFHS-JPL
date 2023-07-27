# FFHS-JPL

[![License](https://img.shields.io/badge/License-GPL--v3.0-lightgrey)](https://github.com/rumpli/FFHS-JPL/blob/main/LICENSE)
![Code](https://img.shields.io/badge/Language-Java-blue)

-----

Table of Contents
=================
* [What is this about?](#what-is-this-about)
* [Multiplayer quiz - requirements](#multiplayer-quiz-requirements)
* [Evaluation and grade](#evaluation-and-grade)
* [More FFHS projects](#more-ffhs-projects)

-----

### What is this about?
This project is the semester work from the Java Project Level module at the FFHS (FS23). This Java project was written with IntelliJ and uses gradle as build automation tool.
There are multiple projects to choose from, this one implements the *Multiplayer Quiz*.

### Multiplayer quiz requirements
Programmieren Sie eine Client-Server-Applikation, bei der mehrere Spieler in einem Quiz gegeneinander antreten. Jede Quizfrage bietet jeweils vier Antwortmöglichkeiten A bis D, wobei die schnellste richtige Antwort gewinnt.

Anforderungen
- Sie dürfen den gesamten Unicode-Zeichensatz verwenden.
- Die Kommunikation zwischen den Clients und dem Server erfolgt direkt über Sockets. Alternativ können Sie auch eine Webanwendung implementieren.
- Der Server liest die Textdatei mit den Quizfragen ein. Eine Quizfrage besteht aus einer Frage mit jeweils vier Antwortmöglichkeiten A bis D (wie bei «Wer wird Millionär?»). Diese Textdatei wird Ihnen von Ihrem Dozenten gestellt.
- Die Spieler/Clients bekommen vom Server jeweils gleichzeitig eine Frage mit den vier Antwortmöglichkeiten zugesandt.
- Der Spieler/Client mit der schnellsten richtigen Antwort bekommt einen Punkt.
- Jeder Spieler/Client wird über den aktuellen Spielstand informiert.
- Sie dürfen kein Java Remote Method Invocation (RMI) und keine Drittbibliotheken verwenden, mit Ausnahme von JUnit.
- Sicherheitsaspekte und die Behandlung von Verbindungsunterbrüchen können Sie ausser Acht lassen.
- Das User Interface kann als Text UI auf der Konsole, als Web- oder als JavaFX Anwendung umgesetzt werden.

### Evaluation and grade
Grade: 5.0

Evaluation:
- Das Spiel laesst sich builden und es laeuft. Die Bedienung ist allerdings gewoehnungsbeduerftig und sehr unflexibel. Muehsam ist auch dass der Server nach jedem Spiel herunterfaehrt und neu gestartet werden muss.
- Das Spiel laueft auch ueber das Netzwerk ohne erkennbare Probleme.
- Was bedeuten die Haus-Bezeichnungen wie Gryffindor?
- Viele Tests sind vollkommen nutzlos weil sie gar nichts testen, zum Beispiel die beiden "Testklassen" im server-Directory.
- Positiv: alle Methoden haben Javadoc-Kommentare.
- Der Code ist vernuenftig in Packages aufgeteilt.
- Records waren nicht im Modulplan, aber ich haette wenigstens den Kommentar geloescht...
// IntelliJ rewrote previous class to record
// What is a record? https://www.developer.com/java/java-record-class/#:~:text=Record%20is%20a%20special%20purpose,added%20into%20the%20Java%20language.
- Gute Anwendung fuer das CountdownLatch im Server.
- public class Client extends Player /
Player sollte eher ein Attribut vom Client sein (uses Player)
- Dokumentation: keine

### More FFHS projects

FFHS directory: [MyCloud](https://www.mycloud.ch/s/S00735653476C6FF89DAE1C9D6F19C814A0FE9C6DC2)

![image](https://github.com/rumpli/FFHS-AnPy/assets/24840091/5c56fb5b-944a-40a3-b5c8-1972850dc7a2)

FFHS projects: [GitHub](https://github.com/rumpli?tab=repositories&q=FFHS&type=&language=&sort=)
