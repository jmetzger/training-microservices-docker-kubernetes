# Indikatoren für den Wechsel von Monolith zu Microservices

## Harte Faktoren (messbar)
- **Deployment-Frequenz**: Deployments dauern >1h oder nur wenige Male pro Monat möglich
- **Build-Zeit**: >30 Minuten für vollständigen Build
- **Team-Größe**: >15 Entwickler arbeiten am gleichen Codebase
- **Codebase-Größe**: >100k LOC oder >50 Module
- **Ausfallrate**: Einzelne Bugs führen zu Totalausfall
- **Skalierungskosten**: Gesamte Anwendung muss skaliert werden, obwohl nur Teile Last haben
- **Time-to-Market**: Neue Features brauchen Wochen/Monate statt Tage

## Weiche Faktoren
- **Entwickler-Produktivität**: Änderungen benötigen umfangreiche Regressionstests
- **Technologie-Lock-in**: Neue Technologien nicht einsetzbar
- **Team-Autonomie**: Teams blockieren sich gegenseitig
- **Onboarding**: Neue Entwickler brauchen Wochen zum Verständnis
- **Code-Ownership**: Unklare Verantwortlichkeiten

---

## Prompt für Migrationsanalyse

```
Analysiere unsere Anwendung auf Eignung für Microservices-Migration:

## Kontext
- Beschreibung: [Anwendungstyp und Domäne]
- Team-Größe: [Anzahl Entwickler]
- Deployment-Frequenz: [Wie oft?]
- Build/Deploy-Dauer: [Zeit in Minuten]

## Probleme
- Aktuelle Schmerzpunkte: [Liste der Probleme]
- Business-Anforderungen: [Skalierung, TTM, etc.]

## Infrastruktur
- Aktueller Stack: [Technologien]
- DevOps-Reifegrad: [CI/CD, Monitoring, etc.]
- Cloud/On-Premise: [Infrastruktur]

Bewerte:
1. Dringlichkeit der Migration (1-10)
2. Risiken und Voraussetzungen
3. Empfohlene Strategie (Big Bang vs. Strangler Pattern)
4. Erste Kandidaten für Extraktion
5. Geschätzte Kosten vs. Nutzen
```
