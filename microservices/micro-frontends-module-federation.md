# Module Federation — Micro-Frontends zur Laufzeit laden

## Was ist Module Federation?

Module Federation ist ein Feature von **Webpack 5** (seit 2020), das es erlaubt,
JavaScript-Module aus einem anderen laufenden Deployment zu laden —
zur Laufzeit, nicht beim Build.

Das ist der technische Kern hinter Laufzeit-Micro-Frontends:
Jedes Team deployt sein MFE eigenstaendig, die Shell-App laedt es dynamisch nach.

![Module Federation: Build-Zeit vs. Laufzeit](/images/micro-frontend-module-federation.svg)

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
      name: 'catalog',           // eindeutiger Name des Remote
      filename: 'remoteEntry.js', // Einstiegspunkt fuer den Host

      exposes: {
        // Schluessel: wie der Host das Modul importiert
        // Wert:       lokaler Pfad zur Datei
        './CatalogApp':    './src/App',
        './ProductCard':   './src/components/ProductCard',
      },

      shared: {
        react:     { singleton: true, requiredVersion: deps.react },
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
// catalog-mfe/src/bootstrap.tsx  (wichtig — siehe Hinweis unten)
import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';

ReactDOM.render(<App />, document.getElementById('root'));
```

```tsx
// catalog-mfe/src/index.ts
import('./bootstrap'); // dynamischer Import — Pflicht bei Module Federation
```

**Warum `bootstrap.tsx`?**
Ohne den dynamischen Import wuerde Webpack React "eager" laden — bevor
der Host die shared-Konfiguration aushandeln kann. Das fuehrt zu zwei
React-Instanzen im Browser und zu Fehlern. Der Umweg ueber `bootstrap`
gibt Webpack die noetige Zeit.

---

### Host konfigurieren: Shell-App

Die Shell-App weiss zur Build-Zeit, welche Remotes es gibt.
Die URL kann aber auch zur Laufzeit gesetzt werden (Dynamic Remotes, s.u.).

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
        catalog:  'catalog@http://localhost:3001/remoteEntry.js',
        cart:     'cart@http://localhost:3002/remoteEntry.js',
      },

      shared: {
        react:     { singleton: true, requiredVersion: deps.react },
        'react-dom': { singleton: true, requiredVersion: deps['react-dom'] },
      },
    }),
  ],
};
```

```tsx
// shell-app/src/App.tsx
import React, { Suspense } from 'react';

// Lazy import aus dem Remote — wird erst beim Rendern geladen
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

```tsx
// shell-app/src/index.ts
import('./bootstrap'); // gleicher Trick wie im Remote
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

declare module 'catalog/ProductCard' {
  interface Props { productId: string; }
  const ProductCard: React.ComponentType<Props>;
  export default ProductCard;
}

declare module 'cart/CartApp' {
  const CartApp: React.ComponentType;
  export default CartApp;
}
```

---

### Starten (lokale Entwicklung)

```
# Terminal 1: Katalog-MFE starten
cd catalog-mfe && npm start   # http://localhost:3001

# Terminal 2: Warenkorb-MFE starten
cd cart-mfe && npm start      # http://localhost:3002

# Terminal 3: Shell starten
cd shell-app && npm start     # http://localhost:3000
```

Die Shell unter `localhost:3000` laedt automatisch die MFEs von
`localhost:3001` und `localhost:3002`.

---

## Dynamic Remotes — URL aus Config laden

In Produktion sind die URLs Umgebungsvariablen, keine hartkodierten Strings.

```javascript
// shell-app/webpack.config.js — dynamisch
new ModuleFederationPlugin({
  name: 'shell',
  remotes: {
    catalog: `catalog@${process.env.CATALOG_URL}/remoteEntry.js`,
    cart:    `cart@${process.env.CART_URL}/remoteEntry.js`,
  },
  // ...
})
```

Oder zur echten Laufzeit (URL aus API/Config-Endpoint):

```typescript
// shell-app/src/loadRemote.ts
async function loadRemote(url: string, scope: string, module: string) {
  // Webpack __webpack_init_sharing__ / __webpack_share_scopes__
  await __webpack_init_sharing__('default');

  const container = window[scope as any] as any;
  await container.init(__webpack_share_scopes__.default);

  const factory = await container.get(module);
  return factory();
}

// Verwendung
const { default: CatalogApp } = await loadRemote(
  'https://catalog.shop.de/remoteEntry.js',
  'catalog',
  './CatalogApp'
);
```

---

## Vite-Alternative: `@originjs/vite-plugin-federation`

Fuer neuere Projekte mit Vite funktioniert das Plugin analog zu Webpack:

```javascript
// catalog-mfe/vite.config.ts
import { defineConfig } from 'vite';
import federation from '@originjs/vite-plugin-federation';

export default defineConfig({
  plugins: [
    federation({
      name: 'catalog',
      filename: 'remoteEntry.js',
      exposes: {
        './CatalogApp': './src/App.tsx',
      },
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
      remotes: {
        catalog: 'http://localhost:3001/assets/remoteEntry.js',
      },
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
| TypeScript-Fehler `Cannot find module 'catalog/...'` | Fehlende Typdeklaration | `declarations.d.ts` anlegen |
| MFE laedt nicht in Produktion | URL stimmt nicht | `remoteEntry.js`-URL pruefen, CORS-Header setzen |
| Weisser Bildschirm ohne Fehlermeldung | `eager` Consumption | `index.ts` nur `import('./bootstrap')` — nichts anderes |
| Verschiedene React-Versionen | Inkompatible `shared`-Config | `requiredVersion` in allen MFEs gleich setzen |

---

## Weiterfuehrendes

- [Micro-Frontends — Grundlagen und Schnittstrategien](micro-frontends.md)
- [MFE-Kommunikation — Custom Events, Shell, Backend-Zustand](micro-frontends-kommunikation.md)
