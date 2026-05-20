# SEED vs. DDD mit EventStorming — Wann nehme ich was?

## Kurzuebersicht

| Kriterium | SEED | DDD + EventStorming |
|---|---|---|
| Ausgangspunkt | Akteure und ihre Jobs (JTBD) | Domaenen-Events |
| Richtung | Top-down: Akteur → Job → API | Bottom-up: Event → Aggregat → Service |
| Fokus | API-Design und Schnittstellen | Domaenenverstaendnis und Schnittfindung |
| Ergebnis | OpenAPI-Spezifikation | Bounded Contexts, Ubiquitous Language |
| Teamformat | Kleines Team, Entwickler-nah | Workshop mit Fachexperten und Devs |
| Wann ideal | Neue Services entwerfen (greenfield) | Monolith verstehen und schneiden |

---

## SEED — Service Experience Envelope Design

**Kernfrage:** *Wer will was tun, und welche API braucht er dafuer?*

SEED geht von Akteuren aus (z.B. Kunde, BFF-API, anderer Microservice) und
beschreibt deren "Jobs to be Done" (JTBD). Daraus werden Actions und Queries
abgeleitet und direkt als OpenAPI-Spec beschrieben.

### Vorgehensweise (7 Schritte)

1. Akteure identifizieren
2. Jobs der Akteure ermitteln (JTBD)
3. Interaktionsschritte mit Ablaufdiagrammen entwickeln
4. Actions und Queries ableiten
5. Als OpenAPI-Spezifikation beschreiben
6. Feedback zur API einholen
7. Implementieren

### Staerken von SEED

- Schnell zu einem konkreten API-Vertrag
- Gut geeignet, wenn die Domaene schon klar ist
- Entwickler koennen direkt loslegen (OpenAPI → Code)
- Verhindert "zu viele Features" durch JTBD-Fokus

### Schwaechen von SEED

- Setzt voraus, dass die Domaene und die Servicegrenzen bekannt sind
- Deckt keine versteckten Geschaeftsprozesse auf
- Weniger geeignet fuer das Aufteilen eines gewachsenen Monolithen

---

## DDD + EventStorming

**Kernfrage:** *Was passiert in der Domaene, und wo liegen die natuерlichen Grenzen?*

EventStorming bringt Fachexperten und Entwickler zusammen, um alle
relevanten **Domaenen-Events** an einer Pinnwand zu sammeln (orange Karten).
Daraus entstehen Aggregate, Bounded Contexts und schliesslich Servicegrenzen.

### Vorgehensweise (EventStorming grob)

1. Events sammeln (Was passiert im System? z.B. `BestellungAufgegeben`)
2. Events zeitlich sortieren
3. Commands und Akteure hinzufuegen (Was loest den Event aus?)
4. Aggregate identifizieren (Welche Daten gehoeren zusammen?)
5. Bounded Contexts abgrenzen (Wo aendert sich die Sprache/Verantwortung?)
6. Serviceschnitt ableiten

### Staerken von DDD/EventStorming

- Legt versteckte Komplexitaet und Konflikte offen
- Schafft gemeinsames Verstaendnis zwischen Fachbereich und Technik
- Ideal zum Schneiden eines Monolithen
- Ergebnis sind stabile Servicegrenzen (nicht nur APIs)

### Schwaechen von DDD/EventStorming

- Zeitaufwaendig (halber bis ganzer Tag fuer komplexe Domaenen)
- Braucht echte Fachexperten im Raum
- Liefert keine fertige API-Spezifikation

---

## Die Kombination: SEED + DDD

In der Praxis ergaenzen sich beide Methoden hervorragend:

```
EventStorming
    └─► Bounded Contexts gefunden
            └─► Pro Service: SEED anwenden
                    └─► OpenAPI-Spezifikation
                            └─► Implementierung
```

**Konkret:**
- EventStorming klaert *welche Services* es geben soll
- SEED klaert *welche API* jeder Service nach aussen anbietet

### Beispiel: ShopMax

```
EventStorming ergibt:
  - Bounded Context "Bestellungen"
  - Bounded Context "Versand"
  - Bounded Context "Lager"

Danach SEED fuer den Bestellungs-Service:
  Akteur: Frontend-App
  JTBD:   Kunde will Bestellung aufgeben
  Action: POST /orders
  Query:  GET /orders/{id}
  → OpenAPI-Spec erstellen
```

---

## Entscheidungshilfe

```
Ist die Domaene unklar oder ein Monolith vorhanden?
  → JA  → EventStorming zuerst, dann SEED je Service
  → NEIN

Sind die Servicegrenzen bereits bekannt?
  → JA  → SEED direkt anwenden
  → NEIN → EventStorming fuer den unklaren Teil
```

---

## Fazit

| Situation | Empfehlung |
|---|---|
| Neues Projekt, Domaene klar | SEED |
| Monolith schneiden | EventStorming → dann SEED |
| Team-Workshop mit Fachbereich | EventStorming |
| API-Spec fuer bekannten Service | SEED |
| Komplexe, unbekannte Domaene | EventStorming + DDD |
