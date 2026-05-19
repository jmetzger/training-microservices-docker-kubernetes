# Auswertung EventStorming — ShopMax

Auswertung der Teilnehmer-Ergebnisse aus Schritt 1 der Uebung
"Monolith schneiden mit DDD und Strangler Fig Pattern".

---

## Gefundene Events (Gruppe)

```
KundeRegistriert        EmailVerifiziert        ProduktGesucht
ArtikelInWarenkorbHinzugefuegt
BestellungDurchgefuehrt BestellungStorniert     KundenBestaetigung(Verschickt)
ZahlungFehlgeschlagen   ZahlungErstattet        RechnungErstellt
BestandAbgefragt        BestandGeaendert        NachbestellungAusgeloest
VersandLabelErstellt    PaketVersendet          VersandBestaetigung(Erhalten)
RetoureRegistriert      RetoureLabelErstellt    RetoureErhalten
RabattCode
```

**Anzahl: ~20 Events — das liegt im guten Bereich (Ziel: 18-30).**

---

## Positiv aufgefallen

- **EmailVerifiziert** — wird oft vergessen, hier gefunden ✓
- **ZahlungFehlgeschlagen** — Fehlerfall explizit beruecksichtigt ✓
- **NachbestellungAusgeloest** — zeigt Denken ueber Lagerbestand hinaus ✓
- **RetoureRegistriert / RetoureLabelErstellt / RetoureErhalten** — dreiteiliger Retoure-Prozess vollstaendig ✓
- **VersandLabelErstellt** — guter Zwischenschritt vor dem Versand ✓

---

## Anmerkungen und Korrekturen

### RabattCode — kein Domain Event

`RabattCode` ist ein **Objekt**, kein Ereignis.
Domain Events stehen in der Vergangenheitsform und beschreiben etwas, das passiert ist.

```
Falsch:  RabattCode
Richtig: RabattcodeEingeloest
         RabattcodeErstellt
         RabattcodeAbgelaufen
```

### BestandAbgefragt — eher technisches Event

Eine Abfrage (Query) ist kein Geschaeftsereignis — sie veraendert keinen Zustand
und loest keine Reaktion in anderen Contexts aus.

```
Raus:    BestandAbgefragt
Behalten: BestandGeaendert, NachbestellungAusgeloest
```

### ProduktGesucht — Grenzfall

Streng genommen ist eine Suche kein Domain Event (kein Zustandswechsel, keine Reaktion).
Im Kontext von Analytics oder Recommendation Engines koennte es eines sein.
Fuer ShopMax: eher raus.

### PaketVersendet — richtig, aber LieferungZugestellt fehlt

`PaketVersendet` ist korrekt und war gut gefunden.
Es fehlt jedoch `LieferungZugestellt` als **zusaetzliches** Event.
Beide beschreiben verschiedene Zeitpunkte mit verschiedenen fachlichen Konsequenzen:

```
[PaketVersendet]                      [LieferungZugestellt]
 Paket verlaesst das Lager             Zusteller scannt beim Kunden
 → VersandEmail wird ausgeloest        → Rueckgabefrist beginnt
 → Tracking-Nummer wird aktiv          → Rechnungsziel beginnt (Zahlung auf Rechnung)
                                       → Bestellprozess formal abgeschlossen
```

Typische Ursache: Man denkt aus Shop-Perspektive ("wir haben versendet")
und vergisst die Kundenperspektive ("Paket ist angekommen").

---

## Vergessene Events

| Fehlendes Event | Warum wichtig |
|---|---|
| KundeAngemeldet | Login ist ein Geschaeftsereignis — relevant fuer Security, Session, Fraud-Detection |
| ProfilAktualisiert | "Profil pflegen" steht im Funktionsumfang — hat eigene Fachlogik (z.B. Adresse fuer Versand) |
| PasswortGeaendert | Eigener Sicherheits-Flow (Bestaetigung, Invalidierung von Sessions) |
| ProduktAngelegt | Wer legt Produkte an? Eigenes Team, eigene Fachlogik |
| PreisGeaendert | Preisaenderungen haben Downstream-Effekte (Warenkorb, laufende Bestellungen) |
| WarenkorbGeleert / WarenkorbAbgebrochen | Session laeuft ab — relevant fuer Analytics und Remarketing |
| BestellungBestaetigt | Zwischenzustand zwischen AufgegebenAndVersendet — loest oft Lagerreservierung aus |
| LagerbestandKritisch | Schwellenwert unterschritten — loest Einkaufsprozess aus, bevor Bestand auf 0 faellt |
| ArtikelAusverkauft | Bestand = 0 ist ein eigenes Event mit eigenen Reaktionen (Produktseite, Bestellsperre) |
| BestellbestaetigunsEmailVersendet | Benachrichtigungen sind eigene Events — sie koennen fehlschlagen und muessen nachverfolgt werden |
| VersandEmailVersendet | Gleicher Grund — der Versand der Email ist ein eigenes verfolgbares Ereignis |

### ZahlungErfolgt — groesste inhaltliche Luecke

`ZahlungErfolgt` fehlt vollstaendig.
Die Gruppe hat `ZahlungFehlgeschlagen` und `ZahlungErstattet` gefunden, aber nicht den Normalfall.

`ZahlungErfolgt` loest mehr Downstream-Reaktionen aus als jedes andere Event:

```
ZahlungErfolgt
    → LagerbestandAktualisiert         (Reservierung wird fest)
    → LieferungVorbereitet             (Versand wird angestossen)
    → RechnungErstellt
    → BestellbestaetigunsEmailVersendet
```

Typische Ursache: Man denkt zuerst an Fehler ("was kann schiefgehen?")
und vergisst den Erfolgsfall explizit als Event zu benennen.

---

## Zusammenfassung fuer die Gruppe

| Kategorie | Bewertung |
|---|---|
| Anzahl Events | Gut (ca. 20) |
| Fehlerfaelle | Gut — ZahlungFehlgeschlagen explizit genannt |
| Retoure-Prozess | Sehr gut — alle drei Schritte gefunden |
| Format | Ein Fehler: RabattCode ist kein Event |
| Technische Events | BestandAbgefragt und ProduktGesucht hinterfragen |
| Groesste Luecke | Kundenverwaltung (Login, Profil) und ZahlungErfolgt fehlen |

> **Wichtigste Erkenntnis:**
> Die gefundenen Events decken vor allem den Bestellprozess gut ab.
> Kundenverwaltung und Produktpflege sind unterrepraesentiert —
> das wird sich in der naechsten Aufgabe zeigen.
