# End-to-End Testing (E2E)

## Was testen E2E-Tests?

E2E-Tests simulieren einen echten Nutzer durch das gesamte System — vom Browser bis zur
Datenbank, ueber alle Services hinweg. Kein Mock, keine Abkuerzung.

```
E2E-Test (z.B. Playwright)
       |
       v
  [ Browser / API-Client ]
       |
       v
  [ Frontend / API Gateway ]
       |
       +-------> [ Order Service ]     --> [ PostgreSQL ]
       |
       +-------> [ Inventory Service ] --> [ MongoDB ]
       |
       +-------> [ Payment Service ]   --> [ Stripe API ]
       |
       v
  Pruefe: Bestellung angelegt, Email verschickt, Lager reduziert
```

**Was Unit/Integration Tests NICHT finden, findet E2E:**
- Fehler im Zusammenspiel mehrerer Services
- Falsche Konfiguration (z.B. falscher Service-URL in Produktion)
- Timing-Probleme bei asynchronen Prozessen

---

## Die goldene Regel: Wenige, kritische Journeys

```
Falsch: 500 E2E-Tests — 2h Laufzeit, schlagen oft zufaellig fehl
Richtig: 10 E2E-Tests fuer die wichtigsten Geschaeftsprozesse
```

**Was sind kritische Journeys?**
- Checkout / Bezahlung
- Login / Registrierung
- Kernprozess des Produkts (z.B. Buchung, Bestellung, Versand)

Alles andere: Unit- oder Integration-Test.

---

## Beispiel mit Playwright (TypeScript)

```bash
npm install @playwright/test
npx playwright install
```

```typescript
// tests/checkout.spec.ts
import { test, expect } from '@playwright/test';

test('Nutzer kann Produkt kaufen', async ({ page }) => {
    // Schritt 1: Produkt suchen
    await page.goto('http://localhost:3000');
    await page.fill('[data-testid="search"]', 'Laptop');
    await page.click('[data-testid="search-button"]');

    // Schritt 2: Zum Warenkorb hinzufuegen
    await page.click('[data-testid="product-laptop"]');
    await page.click('[data-testid="add-to-cart"]');

    // Schritt 3: Checkout
    await page.click('[data-testid="checkout"]');
    await page.fill('[data-testid="card-number"]', '4242 4242 4242 4242');
    await page.click('[data-testid="pay"]');

    // Pruefe: Bestaetigung erscheint
    await expect(page.locator('[data-testid="order-confirmation"]')).toBeVisible();

    // Pruefe: Order-ID im System vorhanden (API-Check)
    const orderId = await page.locator('[data-testid="order-id"]').textContent();
    const response = await page.request.get(`/api/orders/${orderId}`);
    expect(response.status()).toBe(200);
});
```

---

## Resiliente E2E-Tests schreiben

E2E-Tests schlagen oft fehl wegen Timing-Problemen (asynchrone Services, Netzwerk).

### Warten statt feste Sleeps

```typescript
// SCHLECHT: feste Wartezeit
await page.waitForTimeout(3000);  // Warum 3s? Zu kurz auf langsamem Server

// GUT: auf Element warten
await page.waitForSelector('[data-testid="order-confirmation"]', { timeout: 10000 });

// GUT: auf API-Status pollen (z.B. Email-Versand)
await expect.poll(
    async () => {
        const resp = await page.request.get('/api/orders/123/status');
        return (await resp.json()).emailSent;
    },
    { timeout: 15000, intervals: [500, 1000, 2000] }
).toBe(true);
```

### Idempotente Tests

```typescript
// Jeder Test beginnt mit sauberem Zustand
test.beforeEach(async ({ request }) => {
    await request.post('/api/test/reset-db');
});
```

---

## Wann E2E-Tests ausfuehren?

| Wann | Warum |
|---|---|
| **Nicht bei jedem PR** | Zu langsam (30-60 Min.), zu flakey |
| **Nightly** | Vollstaendige Validierung des Gesamtsystems |
| **Vor Release** | Letztes Sicherheitsnetz |
| **Smoke Test nach Deploy** | Kurzer Check: laeuft das System noch? |

---

## Tools im Ueberblick

| Tool | Typ | Staerke |
|---|---|---|
| **Playwright** | Web E2E | Schnell, zuverlaessig, multi-browser, auch API-Testing |
| **Cypress** | Web E2E | Gute Developer Experience, viele Plugins |
| **Selenium** | Web E2E | Klassiker, breite Browser-Unterstuetzung |
| **Puppeteer** | Chrome-Steuerung | Einfach, nur Chromium |
| **k6** | Last/Performance | API-Last-Tests (kein funktionales Testing) |
| **Appium** | Mobile | iOS/Android E2E-Tests |

**Empfehlung fuer Microservices:** Playwright — unterstuetzt auch direkte API-Calls
ohne Browser, ideal um zu pruefen ob Services korrekt zusammenspielen.

---

## Anti-Patterns

| Anti-Pattern | Problem | Besser |
|---|---|---|
| Feste `sleep()`-Aufrufe | Test flakey trotzdem langsam | `waitForSelector` / `waitFor` |
| Jedes Feature mit E2E testen | Zu langsam, zu teuer | Nur kritische Journeys |
| Geteilter Test-State | Tests beeinflussen sich | DB-Reset vor jedem Test |
| Externe APIs direkt aufrufen | Flakey durch externe Ausfaelle | Mock-Endpunkte fuer Drittdienste |
