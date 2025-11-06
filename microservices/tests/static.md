# Static Tests

Hier sind prägnante Beispiele für **Static Tests** (Analyse ohne Code-Ausführung):

## Code-Analyse
```python
# Lint-Fehler
def calculate_sum(a b):  # Fehlende Komma
    return a + b
```

## Typ-Prüfung
```typescript
// TypeScript Fehler
let x: number = "text";  // Typkonflikt
```

## Security Scan
```javascript
// Sicherheitslücke
eval(userInput);  // Gefährlich!
```

## Code-Metriken
- Code-Duplikation > 5%
- Funktionslänge > 50 Zeilen

## Tools
- **Linter**: ESLint, Pylint
- **Formatter**: Prettier, Black
- **Security**: SonarQube, Snyk
- **Typen**: TypeScript, mypy

Static Tests finden Fehler **vor** der Ausführung.
