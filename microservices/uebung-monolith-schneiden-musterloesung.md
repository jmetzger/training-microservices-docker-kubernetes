# Musterloesung: Monolith schneiden mit DDD und Strangler Fig Pattern

> **Hinweis fuer Trainer:** Diese Seite enthaelt die Musterloesung fuer die Uebung.
> Nicht an Teilnehmer ausgeben — erst nach der Ergebnis-Praesentation zeigen.

---

## Schritt 1: Domain Events (Musterloesung)

Eine vollstaendige Liste liegt bei 20-25 Events. Wichtig: alle Fehlerfaelle mitnehmen.

```
KundeRegistriert        ProfilAktualisiert      KundeGeloescht
ProduktGespeichert      PreisGeaendert          ProduktGeloescht
LagerbestandAktualisiert LagerbestandKritisch
ArtikelInWarenkorbGelegt WarenkorbGeleert
BestellungAufgegeben    BestellungStorniert     RechnungErstellt
ZahlungErfolgt          ZahlungFehlgeschlagen   RueckzahlungInitiiert
LieferungVersendet      LieferungZugestellt     RetourneEingeleitet
EmailVersendet          SMSVersendet            PushNotificationVersendet
```

**Zeitlinie (Musterloesung):**

```
[frueh]                                                              [spaet]

KundeRegistriert -> ProduktGesucht -> ArtikelInWarenkorbGelegt
  -> BestellungAufgegeben -> ZahlungErfolgt -> LagerbestandAktualisiert
  -> LieferungVersendet -> LieferungZugestellt -> EmailVersendet -> RechnungErstellt

Fehlerfall:
  -> ZahlungFehlgeschlagen -> (Wiederholung oder BestellungStorniert)
  -> RueckzahlungInitiiert -> EmailVersendet
```

Events die im Normalfall zeitlich eng zusammen liegen, deuten auf denselben Context hin
(z.B. ZahlungErfolgt + LagerbestandAktualisiert + LieferungVersendet passieren alle kurz
nach BestellungAufgegeben — moeglicher Hinweis auf engen Bestellprozess-Kern).

**Haeufige Fehler:**
- Nur Erfolgsfaelle (ZahlungErfolgt ohne ZahlungFehlgeschlagen)
- Technische Events statt Business-Events (z.B. "DatenbankEintragErstellt")
- CRUD-Level: "KundeUpdated" statt "ProfilAktualisiert" / "EmailGeaendert"

---

## Schritt 2: Bounded Contexts (Musterloesung)

```
+-------------------+    +-------------------+    +-------------------+
|   Kundenverwaltung |    |    Produktkatalog  |    |   Lagerbestand    |
|                   |    |                   |    |                   |
| KundeRegistriert  |    | ProduktGespeichert |    | LagerbestandAktual|
| ProfilAktualisiert|    | PreisGeaendert     |    | LagerbestandKrit. |
| KundeGeloescht    |    | ProduktGeloescht   |    |                   |
+-------------------+    +-------------------+    +-------------------+

+-------------------+    +-------------------+    +-------------------+
|   Bestellprozess  |    |      Zahlung       |    |  Versand/Logistik |
|                   |    |                   |    |                   |
| ArtikelI.Warenk.  |    | ZahlungErfolgt     |    | LieferungVersendet|
| BestellungAufgeg. |    | ZahlungFehlgeschl. |    | LieferungZugest.  |
| BestellungStorn.  |    | RueckzahlungInit.  |    | RetourneEingel.   |
| RechnungErstellt  |    |                   |    |                   |
+-------------------+    +-------------------+    +-------------------+

+-------------------+
|  Benachrichtigung |
|                   |
| EmailVersendet    |
| SMSVersendet      |
| PushNotification  |
+-------------------+
```

**Diskussionspunkte:**

*Warum Lagerbestand kein Teil von Produktkatalog?*
Produktdaten aendert das Produktmanagement-Team (selten).
Lagerbestand aendert das Lager-Team bei jeder Bestellung (staendig).
Verschiedene Teams, verschiedene Aenderungsfrequenz = verschiedene Contexts.

*Warum Warenkorb kein eigener Context?*
Der Warenkorb ist ein kurzlebiger Zustand im Bestellprozess — keine eigene Fachsprache,
kein eigenes Team, keine eigene DB sinnvoll. Bei sehr grossem Traffic (z.B. Amazon)
koennte er eigener Context werden.

*Woran erkennt man einen zu gross geschnittenen Context?*
Beispiel: Jemand fasst Bestellung, Zahlung und Versand in einem "Bestellprozess"-Context zusammen:

```
Zu gross:
+-----------------------------------------------+
|               Bestellprozess                  |
|                                               |
| BestellungAufgegeben  ZahlungErfolgt          |
| ZahlungFehlgeschlagen LieferungVersendet      |
+-----------------------------------------------+
```

Erkennungszeichen:
- Das Zahlung-Team und das Logistik-Team muessen denselben Context aendern
- Ein Bugfix in der Zahlungslogik blockiert ein Deployment des Versand-Teams
- Die Fachbegriffe kommen aus verschiedenen Abteilungen mit verschiedener Sprache

Korrekt geschnitten: Bestellung, Zahlung und Versand sind drei separate Contexts,
die ueber Events miteinander kommunizieren.

*Ist Benachrichtigung wirklich ein fachlicher Bounded Context?*

Das ist eine berechtigte Frage — und die Antwort haengt vom Unternehmen ab.

Argumente dagegen (eher technischer Service):
- Keine eigene Fachlogik aus der Domaene — "EmailVersendet" ist ein technisches Resultat, kein Geschaeftsereignis
- Kein eigenes Domänenmodell
- Aehnlich wie Logging oder Monitoring: ein Querschnittsservice

Argumente dafuer (eigener fachlicher Context):
- Eigene Geschaeftsregeln: Wer bekommt welche Nachricht ueber welchen Kanal? (SMS deaktiviert, Opt-out, Praeferenzen)
- Eigenes Team: Communications-Team mit Templates, Branding, Versanddienstleister-Anbindung
- Eigene Fehlerbehandlung: Retry-Logik bei fehlgeschlagener Email, Bounce-Management

Fazit fuer ShopMax:
- Kleiner Shop → technischer Infrastruktur-Service, kein eigener Context
- Grosser Shop (Zalando, Amazon) → eigenes Communications-Team mit Personalisierung, A/B-Tests, rechtlichen Anforderungen → eigener fachlicher Context

In der Uebung wurde Benachrichtigung als eigener Context gewaehlt, weil er sich gut
als **erster Schnitt** eignet (keine Abhaengigkeiten, kein kritischer Pfad) —
nicht weil er fachlich zwingend ein eigener Context sein muss.

*Woran erkennt man, dass ein Event im falschen Bounded Context ist?*

| Zeichen | Beispiel aus ShopMax |
|---|---|
| **Falsches Team ist verantwortlich** | `VersandEmailVersendet` im Versand-Context — die Email schickt das Benachrichtigungs-Team, nicht die Logistik |
| **Falsche Ubiquitous Language** | `ProduktGesucht` im Warenkorb — im Warenkorb gibt es keine "Suche", der Begriff gehoert zum Produktkatalog |
| **Context reagiert nie auf dieses Event** | `RetoureErhalten` im Versand — Versand versendet, er empfaengt keine Retouren |
| **Event enthaelt Daten aus einem anderen Context** | `BestellungAufgegeben` mit `zahlungsMethode`-Feld im Bestellprozess — Zahlungsmethode gehoert in den Zahlungs-Context |
| **Andere Contexts muessen hineinsehen um zu reagieren** | Wenn Zahlung auf ein Event im Bestellprozess zugreifen muss, um eigene Events auszuloesen — ist die Grenze falsch gezogen |

---

## Schritt 3: Context Map (Musterloesung)

```
                         +-------------------+
                         |   Produktkatalog  |
                         +-------------------+
                              ^         ^
                              |         |
               +--------------+         +-----------+
               |                                    |
    +-------------------+                +-------------------+
    |   Bestellprozess  |                |   Lagerbestand    |
    +-------------------+                +-------------------+
       |        |    |                            ^
       |        |    +----BestellungAufgegeben--->+
       |        |
       |        +--------BestellungAufgegeben---> +-------------------+
       |                                          |  Benachrichtigung |
       | BestellungAufgegeben                     +-------------------+
       v                                                   ^
    +-------------------+                                  |
    |      Zahlung      |--------ZahlungErfolgt----------->+
    +-------------------+
               |
               | ZahlungErfolgt
               v
    +-------------------+
    |  Versand/Logistik |
    +-------------------+
```

### Integration-Patterns

| Von | Nach | Aktuell (Monolith) | Ziel (Microservices) |
|---|---|---|---|
| Bestellprozess | Zahlung | direkter Methodenaufruf | async Event (BestellungAufgegeben) |
| Bestellprozess | Lagerbestand | direkter DB-Zugriff | async Event (BestellungAufgegeben) |
| Bestellprozess | Benachrichtigung | direkter Methodenaufruf | async Event (BestellungAufgegeben) |
| Zahlung | Versand | direkter Methodenaufruf | async Event (ZahlungErfolgt) |
| Zahlung | Benachrichtigung | direkter Methodenaufruf | async Event (ZahlungErfolgt) |
| Bestellprozess | Produktkatalog | direkter DB-Zugriff | sync REST (Preisabfrage) |

---

## Schritt 4: Strangler Fig — Migrationsreihenfolge (Musterloesung)

### Bewertungsmatrix

> **Skala:** 5 = ideal fuer den ersten Schnitt, 1 = ungeeignet.
> Bei "Wenige Abhaeng.": 5 = kaum andere Contexts haengen daran (leicht herausloesbar),
> 1 = viele Contexts haengen daran (hohes Risiko).

| Context | Wenige Abhaeng. | Business-Wert | Klare Verantwort. | Isolierte DB | **Score** |
|---|---|---|---|---|---|
| Benachrichtigung | 5 | 2 | 5 | 4 | **16** |
| Produktkatalog | 3 | 4 | 4 | 3 | **14** |
| Versand/Logistik | 3 | 3 | 4 | 4 | **14** |
| Zahlung | 2 | 5 | 3 | 3 | **13** |
| Kundenverwaltung | 3 | 3 | 3 | 3 | **12** |
| Bestellprozess | 1 | 5 | 2 | 2 | **10** |
| Lagerbestand | 3 | 4 | 4 | 3 | **14** |

**Ergebnis:** Startkandidat ist **Benachrichtigung**, dann **Produktkatalog** oder **Lagerbestand**.

**Warum Benachrichtigung zuerst?**
- Keine anderen Services haengen davon ab (nur eingehende Events, kein Upstream-Abhaenger)
- Kein kritischer Pfad: Ausfall = keine Email, aber keine Bestellungsfehler
- Gut isolierbar: eigene DB-Tabellen, kein DB-Join mit anderen Services noetig
- Team kann den gesamten Prozess (Strangler Proxy, DB-Migration, Deployment) lernen — ohne Risiko fuer den Checkout-Pfad

**Warum Bestellprozess zuletzt?**
- Fast alle anderen Contexts haengen davon ab
- Geteilte DB mit Foreign Keys zu fast allen Tabellen
- Kritischer Pfad: Ausfall = keine Bestellungen = Umsatzverlust

### Migrationsphasen (konkret fuer ShopMax)

```
Phase 0 (heute):    [Kompletter Monolith]

Phase 1 (Monat 1):  [Monolith ohne Notification] + [Notification Service]
                         Strangler Proxy leitet Notification-Calls um
                         Dual-Write waehrend DB-Migration

Phase 2 (Monat 3):  [Monolith ohne Notification, Produkt, Lager]
                    + [Product Service] + [Inventory Service] + [Notification Service]

Phase 3 (Monat 6):  [Monolith-Kern: Bestellung + Zahlung + Kunde]
                    + [weitere Services]

Phase N:            [Bestellprozess Service] + [Zahlungs Service] + [Kunden Service]
                         Monolith ist "stranguliert" = leer = abgeschaltet
```
