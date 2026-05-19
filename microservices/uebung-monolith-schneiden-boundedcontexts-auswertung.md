# Auswertung Bounded Contexts — ShopMax

Auswertung der Teilnehmer-Ergebnisse aus Schritt 2 der Uebung
"Monolith schneiden mit DDD und Strangler Fig Pattern".

---

## Gefundene Bounded Contexts (Gruppe)

```
Kundenverwaltung      NutzerVerwaltung      Warenkorb
Bestellungsprozess    Zahlungsprozess       Lager
Versand               Retoure (leer)
```

**Anzahl: 8 Contexts — etwas viel, siehe Diskussion unten.**

### Events pro Context

| Context | Events |
|---|---|
| Kundenverwaltung | KundeRegistriert |
| NutzerVerwaltung | EmailVerifiziert, NutzerAngemeldet, PasswortGeaendert |
| Warenkorb | ProduktGesucht, ArtikelInWarenkorbHinzugefuegt, ArtikelInWarenkorbEntfernt, ArtikelAnzahlInWarenkorbGeaendert |
| Bestellungsprozess | BestellungAusgefuehrt, BestellungStorniert, RechnungErstellt, BestellbestaetigunsVerschickt |
| Zahlungsprozess | ZahlungAuthorisiert, ZahlungFehlgeschlagen, ZahlungErstattet |
| Lager | BestandAbgefragt, BestandGeaendert, NachbestellungAusgefuehrt |
| Versand | PaketVersendet, VersandlabelErstellt, VersandbestaetigunsVerschickt, RetoureErhalten |
| Retoure | (leer) |

---

## Positiv aufgefallen

- **NutzerAngemeldet und PasswortGeaendert** — in Schritt 1 noch gefehlt, jetzt gefunden ✓
- **ArtikelAnzahlInWarenkorbGeaendert** — feiner Unterschied zu Hinzufuegen/Entfernen, gut erkannt ✓
- **ZahlungAuthorisiert** statt ZahlungErfolgt — praeziser, trifft den richtigen Moment (Genehmigung durch Bank) ✓
- **Retoure als eigener Context** — der Gedanke ist richtig, auch wenn der Context noch leer ist ✓

---

## Diskussionspunkte

### Kundenverwaltung und NutzerVerwaltung — ein Context, nicht zwei

Die Trennung ist schwer fachlich zu begruenden:
Wer sich registriert, meldet sich auch an und aendert sein Passwort.
Dieselbe Ubiquitous Language, dasselbe Team, dieselbe Datenbank.

```
Zusammenfuehren zu:

+-------------------+
| Kundenverwaltung  |
|                   |
| KundeRegistriert  |
| EmailVerifiziert  |
| NutzerAngemeldet  |
| PasswortGeaendert |
+-------------------+
```

Faustregel: Wenn man keinen Grund nennen kann, warum zwei Teams diese
Contexts getrennt betreiben wuerden — ist es ein Context.

### ProduktGesucht im Warenkorb — falscher Context

Eine Produktsuche gehoert nicht zum Warenkorb.
Der Warenkorb kuemmert sich um Artikel, die bereits ausgewaehlt wurden.
Die Suche findet vorher statt — im Produktkatalog (falls ueberhaupt als Event).

```
Warenkorb:       ArtikelInWarenkorbHinzugefuegt  ✓
                 ArtikelInWarenkorbEntfernt       ✓
                 ArtikelAnzahlGeaendert           ✓

Nicht hier:      ProduktGesucht                  ✗  (eher Produktkatalog oder raus)
```

### RetoureErhalten im Versand — gehoert in Retoure

Der Retoure-Context wurde angelegt, aber leer gelassen.
`RetoureErhalten` wurde in Versand einsortiert — das ist inkonsistent.

Entweder gehoert `RetoureErhalten` in den Retoure-Context,
oder Retoure ist kein eigener Context und die Events werden in Versand zusammengefasst.
Beides ist vertretbar — aber nicht gemischt.

```
Option A: Retoure als eigener Context
+-------------------+
|     Retoure       |
| RetoureErhalten   |
| RetoureLabelErstellt (aus Schritt 1) |
| RetoureRegistriert  (aus Schritt 1) |
+-------------------+

Option B: Retoure als Teil von Versand
+-------------------+
|      Versand      |
| PaketVersendet    |
| VersandlabelErstellt |
| RetoureRegistriert|
| RetoureLabelErstellt |
| RetoureErhalten   |
+-------------------+
```

### BestandAbgefragt im Lager — kein Domain Event

Wie bereits in Schritt 1 angemerkt: eine Abfrage ist kein Geschaeftsereignis.
Sie veraendert keinen Zustand und loest keine Reaktion aus.

```
Raus:     BestandAbgefragt
Behalten: BestandGeaendert, NachbestellungAusgefuehrt
```

### ZahlungAuthorisiert vs. ZahlungErfolgt

`ZahlungAuthorisiert` ist praezise — es ist der Moment, wo die Bank die Zahlung genehmigt.
Aber der Prozess hat zwei Schritte:

```
1. ZahlungAuthorisiert  — Bank gibt gruenes Licht (Reservierung)
2. ZahlungErfolgt       — Geld wird tatsaechlich eingezogen (Capture)
```

Fuer einen einfachen Online-Shop laufen diese oft zusammen.
Bei grossen Bestellungen oder Vorkasse koennen sie auseinanderfallen.
Fuer ShopMax ist `ZahlungAuthorisiert` als einziges Event akzeptabel —
aber der Unterschied sollte bewusst sein.

---

## Fehlender Context: Produktkatalog

Der Produktkatalog fehlt vollstaendig als Context.
Im Funktionsumfang steht: "Produkte werden gepflegt (Beschreibung, Preis, Bilder, Kategorien)".
Wer pflegt die Produkte? Ein anderes Team als das, das Bestellungen bearbeitet.

```
Fehlender Context:
+-------------------+
|   Produktkatalog  |
|                   |
| ProduktAngelegt   |
| PreisGeaendert    |
| ProduktDeaktiviert|
+-------------------+
```

---

## Zusammenfassung fuer die Gruppe

| Kategorie | Bewertung |
|---|---|
| Anzahl Contexts | 8 — etwas viel, Kundenverwaltung/NutzerVerwaltung zusammenfuehren |
| Groesste Staerke | Warenkorb sauber abgegrenzt, Zahlungsprozess praezise |
| Groesste Luecke | Produktkatalog fehlt als eigener Context |
| Konsistenzproblem | Retoure-Context leer, RetoureErhalten in Versand |
| Zu entfernen | BestandAbgefragt (Query, kein Event), ProduktGesucht aus Warenkorb |

> **Faustregel fuer zu viele Contexts:**
> Wenn ein Context nur ein oder zwei Events hat und kein eigenes Team dahintersteht,
> ist er wahrscheinlich Teil eines groesseren Contexts.
