# Monolithen schneiden mit dem Decorating-Collaborator-Pattern

Vergiss den Code – wir denken in Bildern.

## Die Ausgangslage

In deinem Monolithen steckt eine Funktion, sagen wir **"Userdaten abfragen"**. Diese Funktion wird an 20 Stellen im Code aufgerufen. Du willst sie in einen eigenen Service auslagern. Aber: alle 20 Aufrufer auf einen Schlag umzustellen ist zu riskant.

## Die Idee

Du schiebst eine **Schaltstelle** zwischen Aufrufer und Funktion. Von außen sieht alles aus wie vorher – Aufrufer ruft an, Aufrufer bekommt Antwort. Aber *intern* entscheidet die Schaltstelle, was sie tut: nur weiterleiten, parallel testen, oder umrouten. Diese Schaltstelle ist der Decorator.

Der ganze Umbau läuft in vier Schritten:

<svg width="100%" viewBox="0 0 680 460" xmlns="http://www.w3.org/2000/svg" role="img">
<title>Monolithen-Schneiden in vier Phasen mit Schaltstelle</title>
<desc>Vier aufeinanderfolgende Architekturzustände: direkter Aufruf, Schaltstelle eingebaut, Schatten-Modus mit Vergleich, Umstieg auf den neuen Service.</desc>
<style>
text { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif; }
.th { font-size: 14px; font-weight: 500; fill: #2C2C2A; }
.ts { font-size: 12px; fill: #5F5E5A; }
.arr { stroke: #888780; stroke-width: 1.5; fill: none; }
.c-blue > rect { fill: #E6F1FB; stroke: #185FA5; stroke-width: 0.5; }
.c-blue .th { fill: #0C447C; }
.c-blue .ts { fill: #185FA5; }
.c-purple > rect { fill: #EEEDFE; stroke: #534AB7; stroke-width: 0.5; }
.c-purple .th { fill: #3C3489; }
.c-purple .ts { fill: #534AB7; }
.c-gray > rect { fill: #F1EFE8; stroke: #5F5E5A; stroke-width: 0.5; }
.c-gray .th { fill: #444441; }
.c-gray .ts { fill: #5F5E5A; }
.c-teal > rect { fill: #E1F5EE; stroke: #0F6E56; stroke-width: 0.5; }
.c-teal .th { fill: #085041; }
.c-teal .ts { fill: #0F6E56; }
</style>
<defs>
  <marker id="arrow" viewBox="0 0 10 10" refX="8" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
    <path d="M2 1L8 5L2 9" fill="none" stroke="context-stroke" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  </marker>
</defs>

<text class="th" x="40" y="32">1. Vorher</text>
<text class="ts" x="120" y="32">Aufrufer spricht direkt mit dem Monolithen</text>

<g class="c-blue">
  <rect x="80" y="50" width="120" height="50" rx="8"/>
  <text class="th" x="140" y="78" text-anchor="middle" dominant-baseline="central">Aufrufer</text>
</g>
<line x1="200" y1="75" x2="458" y2="75" class="arr" marker-end="url(#arrow)"/>
<g class="c-gray">
  <rect x="460" y="50" width="170" height="50" rx="8"/>
  <text class="th" x="545" y="68" text-anchor="middle" dominant-baseline="central">Monolith</text>
  <text class="ts" x="545" y="86" text-anchor="middle" dominant-baseline="central">enthält Funktion X</text>
</g>

<text class="th" x="40" y="127">2. Schaltstelle einbauen</text>
<text class="ts" x="240" y="127">leitet zunächst 100 % unverändert weiter</text>

<g class="c-blue">
  <rect x="80" y="145" width="120" height="50" rx="8"/>
  <text class="th" x="140" y="173" text-anchor="middle" dominant-baseline="central">Aufrufer</text>
</g>
<line x1="200" y1="170" x2="268" y2="170" class="arr" marker-end="url(#arrow)"/>
<g class="c-purple">
  <rect x="270" y="145" width="120" height="50" rx="8"/>
  <text class="th" x="330" y="163" text-anchor="middle" dominant-baseline="central">Schaltstelle</text>
  <text class="ts" x="330" y="181" text-anchor="middle" dominant-baseline="central">leitet weiter</text>
</g>
<line x1="390" y1="170" x2="458" y2="170" class="arr" marker-end="url(#arrow)"/>
<g class="c-gray">
  <rect x="460" y="145" width="170" height="50" rx="8"/>
  <text class="th" x="545" y="163" text-anchor="middle" dominant-baseline="central">Monolith</text>
  <text class="ts" x="545" y="181" text-anchor="middle" dominant-baseline="central">Funktion X</text>
</g>

<text class="th" x="40" y="222">3. Schatten-Modus</text>
<text class="ts" x="200" y="222">fragt beide, vergleicht, antwortet weiter mit Monolith</text>

<g class="c-blue">
  <rect x="80" y="265" width="120" height="50" rx="8"/>
  <text class="th" x="140" y="293" text-anchor="middle" dominant-baseline="central">Aufrufer</text>
</g>
<line x1="200" y1="290" x2="268" y2="290" class="arr" marker-end="url(#arrow)"/>
<g class="c-purple">
  <rect x="270" y="265" width="120" height="50" rx="8"/>
  <text class="th" x="330" y="283" text-anchor="middle" dominant-baseline="central">Schaltstelle</text>
  <text class="ts" x="330" y="301" text-anchor="middle" dominant-baseline="central">vergleicht</text>
</g>
<line x1="390" y1="278" x2="458" y2="260" class="arr" marker-end="url(#arrow)"/>
<line x1="390" y1="302" x2="458" y2="320" class="arr" marker-end="url(#arrow)" stroke-dasharray="4 3"/>
<g class="c-gray">
  <rect x="460" y="240" width="170" height="40" rx="8"/>
  <text class="th" x="545" y="252" text-anchor="middle" dominant-baseline="central">Monolith</text>
  <text class="ts" x="545" y="270" text-anchor="middle" dominant-baseline="central">echte Antwort</text>
</g>
<g class="c-teal">
  <rect x="460" y="300" width="170" height="40" rx="8"/>
  <text class="th" x="545" y="312" text-anchor="middle" dominant-baseline="central">Neuer Service</text>
  <text class="ts" x="545" y="330" text-anchor="middle" dominant-baseline="central">nur zum Vergleich</text>
</g>

<text class="th" x="40" y="372">4. Umstieg</text>
<text class="ts" x="135" y="372">graduelles Umrouten: 1 % → 10 % → 100 %</text>

<g class="c-blue">
  <rect x="80" y="390" width="120" height="50" rx="8"/>
  <text class="th" x="140" y="418" text-anchor="middle" dominant-baseline="central">Aufrufer</text>
</g>
<line x1="200" y1="415" x2="268" y2="415" class="arr" marker-end="url(#arrow)"/>
<g class="c-purple">
  <rect x="270" y="390" width="120" height="50" rx="8"/>
  <text class="th" x="330" y="408" text-anchor="middle" dominant-baseline="central">Schaltstelle</text>
  <text class="ts" x="330" y="426" text-anchor="middle" dominant-baseline="central">routet</text>
</g>
<line x1="390" y1="415" x2="458" y2="415" class="arr" marker-end="url(#arrow)"/>
<g class="c-teal">
  <rect x="460" y="390" width="170" height="50" rx="8"/>
  <text class="th" x="545" y="408" text-anchor="middle" dominant-baseline="central">Neuer Service</text>
  <text class="ts" x="545" y="426" text-anchor="middle" dominant-baseline="central">übernimmt</text>
</g>
</svg>

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
