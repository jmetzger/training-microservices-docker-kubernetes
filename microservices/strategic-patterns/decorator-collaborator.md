# Beispiel: Decorating-Collaborator-Pattern

## Die Ausgangslage

In deinem Monolithen steckt eine Funktion, sagen wir **"Userdaten abfragen"**. Diese Funktion wird an 20 Stellen im Code aufgerufen. Du willst sie in einen eigenen Service auslagern. Aber: alle 20 Aufrufer auf einen Schlag umzustellen ist zu riskant.

## Die Idee

Du schiebst eine **Schaltstelle** zwischen Aufrufer und Funktion. Von außen sieht alles aus wie vorher – Aufrufer ruft an, Aufrufer bekommt Antwort. Aber *intern* entscheidet die Schaltstelle, was sie tut: nur weiterleiten, parallel testen, oder umrouten. Diese Schaltstelle ist der Decorator.

Der ganze Umbau läuft in vier Schritten:

![Decorator Collaborator Pattern](/images/monolith-schneiden-diagramm.svg)

## Was passiert in jeder Phase

**Phase 1 – Vorher.** Der Ausgangszustand. Du tust noch nichts.

**Phase 2 – Schaltstelle einbauen.** Du bastelst die Schaltstelle in den Monolithen ein und stellst die 20 Aufrufer um, dass sie nicht mehr direkt die Funktion ansprechen, sondern die Schaltstelle. Die Schaltstelle macht aber *erstmal nichts* – sie reicht 1:1 weiter. **In Produktion gehen.** Wenn jetzt was knallt, weißt du: das war ein Bug im Umbau – nicht im neuen Service, denn den gibt es noch gar nicht. Wichtiger Effekt: du hast jetzt einen *einzigen Hebel*, an dem du später drehen kannst.

**Phase 3 – Schatten-Modus.** Jetzt baust du den neuen Service. Die Schaltstelle ruft beide an – Monolith *und* neuen Service – und vergleicht die Antworten. **Der Aufrufer bekommt aber immer noch die Monolith-Antwort.** Der neue Service ist live, aber unsichtbar. Wochenlang Daten sammeln: Stimmen die Antworten überein? Wie schnell ist er? Fehlerquote? Du lernst über den neuen Service unter Echtlast, ohne dass irgendein User es merkt.

**Phase 4 – Umstieg.** Wenn die Schatten-Phase grün ist, fängst du an, *einen kleinen Teil* der Aufrufe wirklich vom neuen Service beantworten zu lassen. Ein Prozent. Über Tage beobachten. Dann zehn. Dann hundert. Bei Problemen: Konfig-Flip zurück. Am Ende kann der alte Funktionsteil aus dem Monolithen gelöscht werden.

## Warum man das so macht

- **Jeder Schritt ist deploybar.** Nicht alles auf einmal.
- **Jeder Schritt ist rückrollbar** – meist mit einem Konfig-Flip, nicht mit einem Code-Rollback.
- **Du lernst, bevor es weh tut.** Schatten-Modus ist ehrliches Feedback unter Echtbedingungen, ohne Risiko für die User.
- **Das Risiko verteilt sich über Wochen** statt sich in einer Migrationsnacht zu ballen.

## Wo das Decorating-Collaborator-Pattern steckt

Die Schaltstelle *ist* der Decorator: sie sitzt zwischen Aufrufer und der eigentlichen Funktion, hat von außen das gleiche Interface, und entscheidet im Inneren selbst, was sie tut – weiterleiten, vergleichen, routen. Der Aufrufer merkt nie, dass dort etwas Neues steht.

Und sobald Monolith und neuer Service über HTTP sprechen, macht Istio genau das in den Phasen 3 und 4 für dich – `mirror` für Schatten-Modus, gewichtetes Routing für den Umstieg. Du brauchst dann keine Schaltstelle mehr im Code, der Sidecar erledigt es.
