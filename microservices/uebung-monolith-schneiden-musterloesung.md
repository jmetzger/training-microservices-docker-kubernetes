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

---

## Schritt 3: Context Map (Musterloesung)

```
                    +-------------------+
                    |   Produktkatalog  |
                    +-------------------+
                         ^         ^
                         |         |
          +--------------+         +--------+
          |                                 |
+-------------------+             +-------------------+
|   Bestellprozess  |             |   Lagerbestand    |
+-------------------+             +-------------------+
     ^         |
     |         | ---publiziert--->
     |         | BestellungAufgegeben
     |         v
+-------------------+    +-------------------+
|      Zahlung      |    |  Benachrichtigung |
+-------------------+    +-------------------+
          |                      ^
          | ---publiziert------> |
          | ZahlungErfolgt ------+
          v
+-------------------+
|  Versand/Logistik |
+-------------------+
```

### Integration-Patterns

| Von | Nach | Aktuell (Monolith) | Ziel (Microservices) |
|---|---|---|---|
| Bestellprozess | Zahlung | direkter Methodenaufruf | sync REST / async Event |
| Zahlung | Versand | direkter Methodenaufruf | async Event (ZahlungErfolgt) |
| Zahlung | Benachrichtigung | direkter Methodenaufruf | async Event |
| Bestellprozess | Lagerbestand | direkter DB-Zugriff | async Event / sync REST |
| Bestellprozess | Produktkatalog | direkter DB-Zugriff | sync REST (Preisabfrage) |

---

## Schritt 4: Strangler Fig — Migrationsreihenfolge (Musterloesung)

### Bewertungsmatrix

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
