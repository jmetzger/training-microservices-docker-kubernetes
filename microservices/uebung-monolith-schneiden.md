# Uebung: Monolith schneiden mit DDD und Strangler Fig Pattern

## Modell und Warum

Diese Uebung verwendet zwei aufeinander aufbauende Techniken:

| Technik | Zweck | Warum |
|---|---|---|
| **DDD Bounded Contexts** | Wo schneiden? | Fachliche Grenzen, nicht technische — der Code folgt der Sprache des Business |
| **Strangler Fig Pattern** | Wie migrieren? | Schrittweise Abloesung, kein riskanter Big-Bang-Rewrite |
| **Event Storming (vereinfacht)** | Was gehoert zusammen? | Sichtbar machen, welche Teile des Systems tatsaechlich zusammenhaengen |

> **Strangler Fig** = eine tropische Pflanze, die langsam um einen Baum waechst und ihn schliesslich ersetzt.
> Der alte Monolith laeuft weiter, waehrend neue Services ihn stueck fuer stueck ablösen.

---

## Ausgangslage: Der Monolith "ShopMax"

ShopMax ist ein 8 Jahre alter Online-Shop, entwickelt in Java/Spring Boot als einzelne
deploybare Einheit. Das Team klagt ueber:

- **Lange Deployments** (40 Min. Test + Build fuer jede Aenderung)
- **Angst vor Releases** (Bugfix in Zahlung bricht Produktsuche)
- **Skalierungsprobleme** (Black Friday: komplette App hochskalieren, obwohl nur Checkout bremst)
- **Team-Konflikte** (3 Teams arbeiten auf derselben Codebase, stoen sich gegenseitig)

### Aktuelle Architektur (vereinfacht)

```
+------------------------------------------------------------+
|                    ShopMax Monolith                        |
|                                                            |
|  UserService    ProductService    OrderService             |
|  CartService    PaymentService    ShippingService          |
|  InventoryService    NotificationService                   |
|                                                            |
|  +------------------+                                      |
|  |   PostgreSQL DB  |  (eine DB, alle Tabellen drin)       |
|  +------------------+                                      |
+------------------------------------------------------------+
         |
    Load Balancer
         |
      Frontend
```

**Alles in einem Prozess. Ein Fehler kann alles "breaken".**

---

## Schritt 1: Domain Events identifizieren (Event Storming)

> **Warum dieser Schritt?**
> Bevor wir schneiden, muessen wir verstehen, was das System *tut*.
> Event Storming zeigt uns die wichtigen Geschaeftsereignisse — und damit die natuerlichen Grenzen.

### Aufgabe (15 Min., Gruppen zu 3-4 Personen)

Schreibt auf Post-its (oder Zettel) alle **Ereignisse** (Domain Events), die in ShopMax
passieren. Format: **Vergangenheitsform**, orange Post-it.

Beispiele als Einstieg:

```
KundeRegistriert       ProduktGesucht         ZahlungFehlgeschlagen
```

**Optional (falls ihr das Gefühl habt, Euch fehlen Events): Ordnet die Events auf einer Zeitlinie an:**

Warum? Events die zeitlich nahe zusammen passieren, gehoeren oft zum selben Bounded Context.
Luecken in der Zeitlinie zeigen, wo noch Events fehlen.

```
[frueh]                                          [spaet]

KundeRegistriert -> ... -> ProduktGesucht -> ... -> ZahlungFehlgeschlagen -> ...
```

### Orientierungshilfe: Wieviele Events sind richtig?

Als Daumenregel gilt: **3-5 Events pro Bounded Context**.
Wieviele Contexts ShopMax hat, erarbeitet ihr in Schritt 2.

| Anzahl Events | Bewertung | Typisches Problem |
|---|---|---|
| **< 10** | Zu wenig | Zu abstrakt — Contexts werden nicht sichtbar, Grenzen bleiben unklar |
| **10-17** | Eher zu wenig | Einige Prozesse fehlen, z.B. Fehlerszenarien (ZahlungFehlgeschlagen) |
| **18-30** | Gut | Deckt Hauptprozesse und wichtige Ausnahmen ab |
| **31-40** | Grenzwertig | Zu technisch, z.B. "EmailAdresseGeaendert" statt "KundeAktualisiert" |
| **> 40** | Zu viel | CRUD-Niveau statt Business-Events — Events sind Implementierungsdetails, keine Geschaeftsereignisse |

**Qualitaetscheck fuer eure Events:**
- Wuerden Business-Analysten (nicht Entwickler) den Begriff verstehen? ✓
- Ist das Event in der Vergangenheitsform formuliert? ✓
- Loest das Event eine Reaktion in mindestens einem anderen Bereich aus? ✓
- Wenn Nein zu allen drei: wahrscheinlich kein Domain Event, sondern ein technisches Detail ✗

### Beobachtung

Markiert Events, die **thematisch zusammengehoeren** mit einer Farbe.
Ihr werdet feststellen: Einige Events gehoeren immer zusammen —
das sind Hinweise auf **Bounded Contexts**.

---

## Schritt 2: Bounded Contexts identifizieren

> **Warum dieser Schritt?**
> Ein Bounded Context ist ein fachlicher Bereich, der eine klare Sprache und
> klare Verantwortung hat. Innerhalb eines Contexts bedeutet "Bestellung" dasselbe.
> Ausserhalb koennte "Bestellung" etwas voellig anderes bedeuten.
>
> **Faustregeln:**
> - Ein Context = ein Team
> - Ein Context = eine Deployeinheit (spaeter)
> - Die Grenzen folgen der **Ubiquitous Language** (gemeinsamer Fachbegriffe)

### Aufgabe

Analysiert eure Events aus Schritt 1 und gruppiert sie in Bounded Contexts:

- Wie viele Contexts findet ihr?
- Gebt jedem Context einen praegnanten Namen aus der Fachsprache des Business
- Welche Events gehoeren dazu?

Nutzt das folgende Raster (ihr braucht moeglicherweise mehr oder weniger Felder):

```
+-------------------+    +-------------------+    +-------------------+
|    Zahlung        |    |                   |    |                   |
|                   |    |                   |    |                   |
|                   |    |                   |    |                   |
|                   |    |                   |    |                   |
+-------------------+    +-------------------+    +-------------------+

+-------------------+    +-------------------+    +-------------------+
|                   |    |                   |    |                   |
|                   |    |                   |    |                   |
|                   |    |                   |    |                   |
|                   |    |                   |    |                   |
+-------------------+    +-------------------+    +-------------------+
```

### Diskussionsfragen

1. Warum ist "Lagerbestand" ein eigener Context und kein Teil von "Produktkatalog"?
   *(Hinweis: Wer aendert Lagerbestand? Wer aendert Produktdaten?)*

2. Koennte "Warenkorb" ein eigener Context sein? Was spricht dafuer, was dagegen?

3. Woran erkennt man, dass man einen Context **zu gross** oder **zu klein** geschnitten hat?
