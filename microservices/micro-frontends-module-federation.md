# Module Federation — Micro-Frontends zur Laufzeit laden

## Was ist Module Federation?

Module Federation ist ein Feature von **Webpack 5** (seit 2020), das es erlaubt,
JavaScript-Module aus einem anderen laufenden Deployment zu laden —
zur Laufzeit, nicht beim Build.

Das ist der technische Kern hinter Laufzeit-Micro-Frontends:
Jedes Team deployt sein MFE eigenstaendig, die Shell-App laedt es dynamisch nach.

![Module Federation: Build-Zeit vs. Laufzeit](/images/micro-frontend-module-federation.svg)

---

## Was ist die Shell-App?

Die Shell-App ist der **aeussere Rahmen** der gesamten Anwendung.
Sie selbst enthaelt kaum eigene Fachlogik — sie stellt den Rahmen bereit,
in den die MFEs zur Laufzeit eingesetzt werden.

```
┌──────────────────────────────────────────────────────┐
│  Shell-App  (der Rahmen)                             │
│  ┌────────────────────────────────────────────────┐  │
│  │  Navigation · Auth-Kontext · Design System     │  │
│  └────────────────────────────────────────────────┘  │
│                                                      │
│  ┌──────────────┐  ┌──────────────┐  ┌───────────┐  │
│  │ MFE: Katalog │  │MFE: Warenkorb│  │MFE: Check-│  │
│  │              │  │              │  │out        │  │
│  └──────────────┘  └──────────────┘  └───────────┘  │
└──────────────────────────────────────────────────────┘
```

**Laeuft die Shell-App auf dem Server?** Nein — sie laeuft vollstaendig im Browser.
Der Server liefert nur die statischen Dateien aus (`index.html`, `main.js`).
Danach ist der Server nicht mehr beteiligt.

```
Server                       Browser des Nutzers
─────────────────            ─────────────────────────────
Gibt Dateien aus:            Fuehrt aus:
  index.html      ──────►     laedt main.js
  main.js         ──────►     Shell-App startet
                              Shell laedt MFEs nach
                              Nutzer sieht die Seite
```

**Warum heisst es Shell-App?** Nichts mit Linux — der Name kommt vom Bild
einer Huelle (Nussschale), die den Inhalt zusammenhaelt.
In der Webpack-Dokumentation heisst dieselbe Rolle **Host**.

| Name | Kontext |
|---|---|
| Shell-App | Branchenbezeichnung fuer MFE-Architektur |
| Host | Webpack-Terminologie |
| Container-App | aeltere Bezeichnung, gleiche Idee |

---

## Vom Code zum Kunden — Schritt fuer Schritt

**Schritt 1: Entwickler schreibt Code (TypeScript / React)**

```
catalog-mfe/src/
├── App.tsx            React-Komponente mit JSX
├── ProductList.tsx
└── api/products.ts    fetch-Aufrufe zum Backend
```

**Schritt 2: Webpack laeuft (lokal oder in CI/CD)**

Webpack liest alle Dateien, uebersetzt TypeScript → JavaScript,
loest alle Imports auf und schreibt das Ergebnis nach `dist/`:

```
catalog-mfe/dist/
├── remoteEntry.js     Einstiegspunkt fuer Module Federation
├── 42.chunk.js        der eigentliche App-Code
└── 891.chunk.js       vendor-Code (z.B. React)
```

**Schritt 3: CI/CD deployt die dist/-Dateien auf einen Webserver / CDN**

```
dist/*.js  ──►  https://catalog.shop.de/
```

Ab hier ist Webpack fertig. Auf dem Server liegen nur fertige `.js`-Dateien.

**Schritt 4: Kunde oeffnet https://shop.meinefirma.de**

```
Browser                         Server: shop.meinefirma.de
  │── GET / ──────────────────────────────────────────► │
  │◄── index.html ────────────────────────────────────  │
  │── GET /main.js ────────────────────────────────────►│
  │◄── main.js (Shell-App) ───────────────────────────  │
```

**Schritt 5: Shell-App startet im Browser und laedt die MFEs nach**

```
Browser                         catalog.shop.de     cart.shop.de
  │── GET /remoteEntry.js ──────────────────────►  │
  │◄── remoteEntry.js ─────────────────────────    │
  │── GET /42.chunk.js (CatalogApp) ─────────────► │
  │◄── 42.chunk.js ────────────────────────────    │
  │                                                     │── GET /remoteEntry.js ──────────────────────────►  │
  │◄── remoteEntry.js ─────────────────────────────── │
  │── GET /77.chunk.js (CartApp) ──────────────────────►│
  │◄── 77.chunk.js ────────────────────────────────── │
```

**Schritt 6: Fertig — der Nutzer sieht die komplette Anwendung**

Alle drei MFEs laufen im gleichen Browser-Tab, obwohl sie von drei verschiedenen
Servern geladen wurden und von drei verschiedenen Teams deployt wurden.
React wurde dabei **nur einmal geladen** (`singleton: true`).

---

## Sprache und Tooling

| Tool | Unterstuetzung | Hinweis |
|---|---|---|
| **Webpack 5** | nativ eingebaut | Standard fuer React/Angular-Projekte |
| **Vite** | Plugin `@originjs/vite-plugin-federation` | Neuere Projekte, schnellerer Dev-Server |
| **TypeScript** | vollstaendig unterstuetzt | Empfohlen — Typen fuer Remote-Module deklarierbar |
| **Framework** | React, Vue, Angular, Svelte, Vanilla JS | Module Federation ist framework-agnostisch |

---

## Die drei Rollen

```
HOST (Shell-App)           REMOTE (MFE)              SHARED
─────────────────          ──────────────             ────────────
laedt Module aus           stellt Module              gemeinsame Libraries
anderen Deployments        bereit (exposes)           (z.B. React)
zur Laufzeit               erzeugt remoteEntry.js     nur einmal geladen
```

---

## Schritt-fuer-Schritt Beispiel

### Voraussetzungen

```
Node.js >= 16
Webpack >= 5
```

### Verzeichnisstruktur

```
shop/
├── shell-app/          # Host
│   ├── src/
│   │   ├── App.tsx
│   │   └── bootstrap.tsx
│   └── webpack.config.js
│
├── catalog-mfe/        # Remote 1
│   ├── src/
│   │   ├── App.tsx
│   │   └── bootstrap.tsx
│   └── webpack.config.js
│
└── cart-mfe/           # Remote 2
    ├── src/
    │   ├── App.tsx
    │   └── bootstrap.tsx
    └── webpack.config.js
```

---

### Remote konfigurieren: Katalog-MFE

Das Katalog-MFE stellt seine App als ladbares Modul bereit.

```javascript
// catalog-mfe/webpack.config.js
const { ModuleFederationPlugin } = require('webpack').container;
const deps = require('./package.json').dependencies;

module.exports = {
  mode: 'development',
  devServer: { port: 3001 },

  plugins: [
    new ModuleFederationPlugin({
      name: 'catalog',            // eindeutiger Name des Remote
      filename: 'remoteEntry.js', // Einstiegspunkt fuer den Host

      exposes: {
        './CatalogApp':  './src/App',
        './ProductCard': './src/components/ProductCard',
      },

      shared: {
        react:       { singleton: true, requiredVersion: deps.react },
        'react-dom': { singleton: true, requiredVersion: deps['react-dom'] },
      },
    }),
  ],
};
```

```tsx
// catalog-mfe/src/App.tsx
export default function CatalogApp() {
  return (
    <div>
      <h2>Produktkatalog</h2>
      <ProductList />
    </div>
  );
}
```

```tsx
// catalog-mfe/src/index.ts
import('./bootstrap'); // dynamischer Import — Pflicht bei Module Federation
```

```tsx
// catalog-mfe/src/bootstrap.tsx
import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';

ReactDOM.render(<App />, document.getElementById('root'));
```

**Warum `bootstrap.tsx`?**
Ohne den dynamischen Import wuerde Webpack React "eager" laden — bevor
der Host die shared-Konfiguration aushandeln kann. Das fuehrt zu zwei
React-Instanzen im Browser. Der Umweg ueber `bootstrap` gibt Webpack die noetige Zeit.

---

### Host konfigurieren: Shell-App

```javascript
// shell-app/webpack.config.js
const { ModuleFederationPlugin } = require('webpack').container;
const deps = require('./package.json').dependencies;

module.exports = {
  mode: 'development',
  devServer: { port: 3000 },

  plugins: [
    new ModuleFederationPlugin({
      name: 'shell',

      remotes: {
        // Format: "<name>@<url>/remoteEntry.js"
        catalog: 'catalog@http://localhost:3001/remoteEntry.js',
        cart:    'cart@http://localhost:3002/remoteEntry.js',
      },

      shared: {
        react:       { singleton: true, requiredVersion: deps.react },
        'react-dom': { singleton: true, requiredVersion: deps['react-dom'] },
      },
    }),
  ],
};
```

```tsx
// shell-app/src/App.tsx
import React, { Suspense } from 'react';

const CatalogApp = React.lazy(() => import('catalog/CatalogApp'));
const CartApp    = React.lazy(() => import('cart/CartApp'));

export default function App() {
  return (
    <div>
      <nav>Shop Navigation</nav>

      <Suspense fallback={<div>Katalog laedt...</div>}>
        <CatalogApp />
      </Suspense>

      <Suspense fallback={<div>Warenkorb laedt...</div>}>
        <CartApp />
      </Suspense>
    </div>
  );
}
```

---

### TypeScript: Typen fuer Remote-Module deklarieren

TypeScript kennt die Remote-Module nicht — sie kommen erst zur Laufzeit.
Ohne Deklaration gibt es einen Compiler-Fehler.

```typescript
// shell-app/src/declarations.d.ts
declare module 'catalog/CatalogApp' {
  const CatalogApp: React.ComponentType;
  export default CatalogApp;
}

declare module 'cart/CartApp' {
  const CartApp: React.ComponentType;
  export default CartApp;
}
```

---

### Starten (lokale Entwicklung)

```
# Terminal 1
cd catalog-mfe && npm start   # http://localhost:3001

# Terminal 2
cd cart-mfe && npm start      # http://localhost:3002

# Terminal 3
cd shell-app && npm start     # http://localhost:3000
```

---

## Dynamic Remotes — URL aus Config laden

In Produktion sind die URLs Umgebungsvariablen, keine hartkodierten Strings.

```javascript
// shell-app/webpack.config.js
new ModuleFederationPlugin({
  name: 'shell',
  remotes: {
    catalog: `catalog@${process.env.CATALOG_URL}/remoteEntry.js`,
    cart:    `cart@${process.env.CART_URL}/remoteEntry.js`,
  },
})
```

---

## Vite-Alternative: `@originjs/vite-plugin-federation`

```javascript
// catalog-mfe/vite.config.ts
import federation from '@originjs/vite-plugin-federation';

export default defineConfig({
  plugins: [
    federation({
      name: 'catalog',
      filename: 'remoteEntry.js',
      exposes: { './CatalogApp': './src/App.tsx' },
      shared: ['react', 'react-dom'],
    }),
  ],
  build: { target: 'esnext' }, // Pflicht fuer Top-Level Await
});
```

```javascript
// shell-app/vite.config.ts
import federation from '@originjs/vite-plugin-federation';

export default defineConfig({
  plugins: [
    federation({
      name: 'shell',
      remotes: { catalog: 'http://localhost:3001/assets/remoteEntry.js' },
      shared: ['react', 'react-dom'],
    }),
  ],
  build: { target: 'esnext' },
});
```

---

## Haeufige Fallstricke

| Problem | Ursache | Loesung |
|---|---|---|
| `Shared module is not available` | React zweimal geladen | `singleton: true` in beiden Configs setzen |
| TypeScript-Fehler `Cannot find module` | Fehlende Typdeklaration | `declarations.d.ts` anlegen |
| MFE laedt nicht in Produktion | URL stimmt nicht | `remoteEntry.js`-URL pruefen, CORS-Header setzen |
| Weisser Bildschirm ohne Fehlermeldung | `eager` Consumption | `index.ts` nur `import('./bootstrap')` — nichts anderes |
| Verschiedene React-Versionen | Inkompatible `shared`-Config | `requiredVersion` in allen MFEs gleich setzen |

---

## Hintergrund: Was ist Webpack?

Browser verstehen kein TypeScript, kein JSX, keine relativen Imports.
Sie verstehen nur plain JavaScript und HTML.

**Webpack** ist ein Build-Tool: Es laeuft einmalig beim Entwickler oder in der CI/CD-Pipeline,
liest den Quellcode und erzeugt daraus fertige `.js`-Dateien, die der Browser direkt laden kann.

```
src/
├── App.tsx        ──┐
├── components/    ──┤  webpack  ──►  dist/main.js  (Browser versteht das)
└── styles.css     ──┘
```

Webpack laeuft ausschliesslich zur Build-Zeit — nie im Browser des Nutzers.

---

## Weiterfuehrendes

- [Micro-Frontends — Grundlagen und Schnittstrategien](micro-frontends.md)
- [MFE-Kommunikation — Custom Events, Shell, Backend-Zustand](micro-frontends-kommunikation.md)
