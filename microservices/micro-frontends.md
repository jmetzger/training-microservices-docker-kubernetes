# Micro-Frontends — Teams am Frontend ohne Kollisionen

## Das Problem: Wo fangen die Konflikte an?

Im Microservices-Backend hat jedes Team klare Grenzen: Team A besitzt den Produkt-Service,
Team B besitzt den Warenkorb-Service. Aber das Frontend? Oft arbeiten alle Teams an denselben
Dateien, im selben Repository, ohne klares Ownership.

Das fuehrt zu:
- Merge-Konflikten und gegenseitigem Blockieren
- Kein Team kann unabhaengig deployen — jede Aenderung braucht Abstimmung
- Unclear, wer fuer welchen Bereich der UI verantwortlich ist

**Die Loesung: das Microservices-Prinzip auf das Frontend uebertragen.**

---

## Kernkonzept: Der vertikale Schnitt

Die entscheidende Frage ist nicht "Wie teilen wir die technischen Schichten auf?"
sondern: **"Welcher Teil der Benutzeroberflaeche gehoert zu welcher Domaene?"**

![Vergleich: Technischer Schnitt vs. Domaenen-Schnitt](/images/micro-frontend-schnittstrategien.svg)

### Technischer Schnitt — das Antipattern

Teams nach Technologie aufzuteilen klingt logisch, erzeugt aber genau das Problem, das wir loesen wollen:

| Team | Zustaendigkeit |
|---|---|
| Team UI | Alle Seiten in React / Vue / Angular |
| Team Plattform | API-Gateway, BFF fuer alle Domains |
| Team Backend A, B, C | Einzelne Services |

Jedes neue Feature benoetigt **Koordination ueber alle drei Teams hinweg** — unabhaengige Arbeit
ist nicht moeglich.

### Domaenen-Schnitt — der richtige Ansatz

Teams nach Geschaeftsdomaene aufzuteilen, sodass jedes Team seinen **vollstaendigen vertikalen Slice** besitzt:

| Team | Frontend | BFF | Services | Datenbank |
|---|---|---|---|---|
| Team Katalog | Produktliste, Suche | Produkt-API | Produkt-Svc, Such-Svc | Produkt-DB |
| Team Warenkorb | Warenkorb-UI | Cart-API | Korb-Svc, Preis-Svc | Korb-DB |
| Team Checkout | Bestell-Formular | Order-API | Bestell-Svc, Zahlung-Svc | Bestell-DB |

**Wichtig:** Der Frontend-Schnitt muss nicht 1:1 zu den Backend-Services passen.
Ein Frontend-Bereich (z.B. Warenkorb) darf mehrere Backend-Services ansprechen —
solange das **Ownership klar im gleichen Team** liegt.

---

## Wie kommen die Teile zusammen? Die Shell-App

Wenn jedes Team sein eigenes Frontend-Stueck baut — wie sieht der Nutzer am Ende
eine zusammenhaengende Anwendung?

Die Antwort: eine **Shell-App (Host-App)** koordiniert das Zusammenfuegen.

![Micro-Frontend Laufzeit-Architektur](/images/micro-frontend-architektur.svg)

Die Shell-App stellt bereit:
- **Navigation und Routing** — welche MFE-URL wird geladen?
- **Shared Design System** — gemeinsame Komponenten (Button, Header, Formularfelder)
- **Auth-Kontext** — Login-Status wird an alle MFEs weitergegeben
- **Fehlerhandling** — was passiert, wenn ein MFE nicht laedt?

---

## Integrationsmuster

Es gibt drei gaengige Wege, Micro-Frontends zusammenzufuehren:

### 1. Build-Time Integration (npm-Packages)

Jedes Team veroeffentlicht sein MFE als npm-Package. Die Shell-App importiert alle Packages
und baut sie gemeinsam.

```
// shell/package.json
{
  "dependencies": {
    "@shop/mfe-catalog":  "^2.1.0",
    "@shop/mfe-cart":     "^1.4.0",
    "@shop/mfe-checkout": "^3.0.0"
  }
}
```

**Vorteil:** Einfach, kein Infrastruktur-Aufwand, Type-Safety moeglich.

**Nachteil:** Aenderung in einem MFE erfordert neuen Build der Shell-App —
kein wirklich unabhaengiges Deployment.

---

### 2. Laufzeit-Integration (Webpack Module Federation)

Die Shell-App laedt MFEs **zur Laufzeit** aus separaten URLs. Jedes Team kann deployen,
ohne dass die Shell-App neu gebaut werden muss.

```javascript
// webpack.config.js der Shell-App
new ModuleFederationPlugin({
  remotes: {
    catalog:  "catalog@https://catalog.shop.de/remoteEntry.js",
    cart:     "cart@https://cart.shop.de/remoteEntry.js",
    checkout: "checkout@https://checkout.shop.de/remoteEntry.js",
  }
})
```

```javascript
// webpack.config.js des Katalog-MFE (wird von der Shell geladen)
new ModuleFederationPlugin({
  name: "catalog",
  filename: "remoteEntry.js",
  exposes: {
    "./CatalogApp": "./src/App"
  }
})
```

**Vorteil:** Echte Entkopplung. Team Katalog kann jederzeit deployen, ohne Team Shell informieren zu muessen.

**Nachteil:** Mehr Infrastruktur-Aufwand, Versionskonflikte bei geteilten Libraries moeglich.

---

### 3. iFrame-basierte Integration

Jedes MFE laeuft in einem eigenen iFrame. Kommunikation ueber `window.postMessage`.

**Vorteil:** Maximale Isolation, kein Risiko durch geteilte Bibliotheken.

**Nachteil:** Schlechte UX (Scrolling, Accessibility, Browser-History), Performance-Overhead.
Nur in Ausnahmefaellen empfehlenswert.

---

## Spielregeln fuer das interdisziplinaere Team

Micro-Frontends loesen viele Koordinationsprobleme — aber nur, wenn klare Vereinbarungen bestehen.

### Das darf jedes Team eigenstaendig entscheiden

- Technologiewahl innerhalb des eigenen MFE (Framework-Version, State-Management, Testing)
- Release-Zeitpunkt des eigenen MFE
- Datenbankschema der eigenen Services
- Interne Architektur des eigenen Frontend-Stuecks

### Das wird gemeinsam festgelegt (und dann nicht mehr geaendert ohne Abstimmung)

| Thema | Beispiel |
|---|---|
| Design System | Gemeinsame Komponenten-Library (`@shop/ui`) |
| API-Kontrakte | Wie spricht die Shell-App mit den MFEs? (Props, Events, Context) |
| Routing-Konvention | `/catalog/*`, `/cart/*`, `/checkout/*` gehoeren wem? |
| Authentifizierung | Wie wird der Auth-Token weitergereicht? |
| Shared Libraries | React-Version, die alle nutzen (vermeidet doppeltes Bundle) |

### Contract Testing fuer MFE-Grenzen

Genau wie Backend-Services per Consumer-Driven Contract Testing integriert werden,
koennen MFEs ihre Schnittstellen absichern:

```javascript
// Team Katalog: definiert, welche Props die Shell erwartet
export interface CatalogAppProps {
  authToken: string;
  onAddToCart: (productId: string) => void;
}
```

Aendert Team Katalog diese Schnittstelle, schlaegt der Contract-Test sofort an —
bevor es zu Laufzeitfehlern in der Shell-App kommt.

---

## Wann lohnt sich das?

Micro-Frontends sind **kein Default** — sie bringen echten Overhead mit sich.

| Szenario | Empfehlung |
|---|---|
| 1-2 Teams am Frontend | Monolithisches Frontend, klare Ordnerstruktur reicht aus |
| 3+ Teams, viele gegenseitige Blockaden | Micro-Frontends erwaegen |
| Teams brauchen wirklich unabhaengige Releases | Module Federation sinnvoll |
| Sehr unterschiedliche Tech-Stacks im Team | iFrame oder Web Components |

### Der haeufigste Fehler

Teams beginnen mit Micro-Frontends, weil es technisch interessant ist — aber
ohne echte Domaenengrenze. Das Ergebnis: die gleichen Koordinationsprobleme wie vorher,
nur mit mehr Infrastruktur.

**Erst Domaene finden, dann schneiden. Nicht andersherum.**

---

## Zusammenfassung

```
Microservice-Prinzip:          Micro-Frontend-Prinzip:
  Service A — DB A               MFE A — Services A — DB A
  Service B — DB B    ──────►    MFE B — Services B — DB B
  Service C — DB C               MFE C — Services C — DB C

Jeder Service: eigenstaendig    Jedes Team: eigenstaendig
deploybar, testbar, skalierbar  deploybar, von UI bis Datenbank
```

Die Schnittlinie liegt **nicht** zwischen UI und Backend,
sondern **vertikal durch alle Schichten**, entlang der Geschaeftsdomaene.

---

## Weiterfuehrende Themen

- [MFE-Kommunikation — Custom Events, Shell, Backend-Zustand](micro-frontends-kommunikation.md)
- [Was sind Microservices?](what-are.md)
- [Bounded Contexts und Domainschnitt](../microservices/monolith-schneiden.md)
- [IAM als Bounded Context](iam-als-bounded-context.md)
- [SEED vs. DDD / EventStorming](seed-vs-ddd-eventstorming.md)
