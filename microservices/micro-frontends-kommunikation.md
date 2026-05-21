# MFE-Kommunikation — Wie reden Micro-Frontends miteinander?

## Das Problem

Micro-Frontends laufen als separate JavaScript-Bundles im Browser.
Sie teilen **keine gemeinsamen Variablen, keinen gemeinsamen Store**.

```
MFE: Katalog                     MFE: Warenkorb
─────────────────                ─────────────────
let cartCount = 0;               let items = [];
                                 
// Diese Variable ist fuer       // ...komplett
// Warenkorb unsichtbar!         // unsichtbar
```

Trotzdem soll ein Klick auf "Kaufen" im Katalog den Warenkorb sofort aktualisieren.

![MFE Kommunikationsmuster](/images/micro-frontend-kommunikation.svg)

---

## Muster 1: Custom Events (empfohlen fuer lose Kopplung)

Der Browser stellt mit `CustomEvent` einen eingebauten Nachrichtenkanal bereit.
Kein Framework, keine gemeinsame Library noetig.

### Sender: Katalog-MFE

```javascript
// CatalogApp.jsx — Button "In den Warenkorb"
function handleBuyClick(product) {
  // 1. Erst den eigenen Service aufrufen
  await fetch('/api/cart/items', {
    method: 'POST',
    body: JSON.stringify({ productId: product.id, qty: 1 })
  });

  // 2. Alle anderen MFEs benachrichtigen
  window.dispatchEvent(
    new CustomEvent('cart:item-added', {
      detail: { productId: product.id, name: product.name, qty: 1 }
    })
  );
}
```

### Empfaenger: Warenkorb-MFE

```javascript
// CartApp.jsx — reagiert auf das Event
useEffect(() => {
  const handler = (event) => {
    const { productId, qty } = event.detail;
    setCartCount(prev => prev + qty);
    setCartVisible(true); // Warenkorb aufklappen
  };

  window.addEventListener('cart:item-added', handler);
  return () => window.removeEventListener('cart:item-added', handler);
}, []);
```

### Warum `window`?

`window` ist das einzige Objekt, das alle MFEs im Browser teilen.
Events auf `window` sind global sichtbar — unabhaengig davon,
in welchem Framework oder Bundle das MFE laeuft.

### Event-Namenskonvention

Prefix mit dem Domaenennamen verhindert Kollisionen:

```
cart:item-added          ✓  klar, welche Domaene
cart:item-removed        ✓
checkout:order-placed    ✓
added                    ✗  zu generisch, Konflikte moeglich
```

---

## Muster 2: Shell-App als Vermittler

Die Shell-App haelt den gemeinsamen Zustand und gibt ihn per Props weiter.

```javascript
// Shell-App: App.jsx
function App() {
  const [cartItems, setCartItems] = useState([]);
  const [cartOpen, setCartOpen]   = useState(false);

  function handleAddToCart(product) {
    setCartItems(prev => [...prev, product]);
    setCartOpen(true); // Warenkorb direkt oeffnen
  }

  return (
    <>
      <CatalogApp onAddToCart={handleAddToCart} />
      <CartApp
        items={cartItems}
        isOpen={cartOpen}
        onClose={() => setCartOpen(false)}
      />
    </>
  );
}
```

**Wann sinnvoll:** Wenn beide MFEs im gleichen Framework (z.B. React) laufen
und der geteilte Zustand ueberschaubar bleibt.

**Problem bei Wachstum:** Die Shell wird zum Flaschenhals, wenn viele MFEs
Zustand teilen muessen ("Prop-Drilling durch die Shell").

---

## Muster 3: Backend als gemeinsamer Zustand

Jedes MFE redet direkt mit dem Backend. Der Warenkorb-Service ist
die einzige Quelle der Wahrheit — keine Frontend-Synchronisation noetig.

### Mit Server-Sent Events (SSE)

```javascript
// CartApp.jsx — abonniert Aenderungen vom Server
useEffect(() => {
  const eventSource = new EventSource('/api/cart/stream');

  eventSource.addEventListener('cart-updated', (event) => {
    const cart = JSON.parse(event.data);
    setCartItems(cart.items);
    setCartCount(cart.totalItems);
  });

  return () => eventSource.close();
}, []);
```

```javascript
// CatalogApp.jsx — schreibt nur in den Service
async function handleBuyClick(productId) {
  await fetch('/api/cart/items', {
    method: 'POST',
    body: JSON.stringify({ productId, qty: 1 })
  });
  // kein Event noetig — Cart-Service benachrichtigt Warenkorb-MFE
}
```

**Vorteil:** Zustand ueberlebt einen Browser-Reload.
Wenn der Nutzer die Seite neu laedt, zeigt der Warenkorb trotzdem den richtigen Stand.

---

## Welches Muster wann?

| Szenario | Empfehlung |
|---|---|
| Verschiedene Frameworks (React + Vue) | Custom Events — framework-agnostisch |
| Gleiches Framework, einfache App | Shell als Vermittler |
| Zustand muss persistiert werden | Backend-Zustand + SSE/Polling |
| Performance-kritisch (viele Updates) | WebSocket statt SSE |
| Einfachste Loesung zuerst | Custom Events, dann bei Bedarf erweitern |

---

## Was man vermeiden sollte

### Direkter Import zwischen MFEs

```javascript
// catalog-mfe/src/App.jsx

// NIEMALS:
import CartStore from 'cart-mfe/src/store'; // harte Kopplung!
CartStore.addItem(product);                 // Katalog haengt jetzt von Warenkorb ab
```

Das zerstoert die Unabhaengigkeit — Katalog-MFE kann nicht mehr
ohne Warenkorb-MFE deployt werden.

### Globale Variablen

```javascript
// NIEMALS:
window.__sharedCart = [];  // Wer ist Owner? Wer rauemt auf?
```

Kein Ownership, kein Lifecycle, schwer zu testen.

### Die Faustregel

> **MFEs kommunizieren ueber Ereignisse oder Vertraege — nicht ueber
> gemeinsamen Code und nicht ueber direkte Referenzen.**

---

## Zusammenfassung: Das "Kaufen"-Beispiel

```
Nutzer klickt "Kaufen" im Katalog-MFE
        │
        ▼
  Katalog-MFE
  ├─ POST /api/cart/items  (Cart-Service speichert)
  └─ window.dispatchEvent('cart:item-added', { ... })
                │
                ▼ (Browser leitet weiter)
        Warenkorb-MFE
        ├─ empfaengt Event
        ├─ aktualisiert Zaehler
        └─ klappt Warenkorb-Drawer auf
```

Die beiden MFEs kennen sich **nicht** — sie kennen nur den Event-Namen.
Das ist lose Kopplung in der Praxis.

---

## Weiterfuehrendes

- [Micro-Frontends — Grundlagen und Schnittstrategien](micro-frontends.md)
