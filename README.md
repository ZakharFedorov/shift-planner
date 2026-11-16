# Webový systém pro plánování směn na recepci kolejí a výpočet odměn

Diplomová práce, Technická univerzita v Liberci. Zadání: [docs/zadani.md](docs/zadani.md).

| Část | Technologie | Složka |
|---|---|---|
| Backend (REST API) | Java 25, Spring Boot 4.1, Spring Security, JPA, Flyway | `backend/` |
| Frontend (SPA) | React 19, TypeScript, Vite, React Router, TanStack Query, i18next | `frontend/` |
| Databáze | PostgreSQL 18 | `backend/compose.yaml` (vývoj) |

## Dokumentace

| Dokument | Obsah |
|---|---|
| [docs/zadani.md](docs/zadani.md) | zadání diplomové práce |
| [docs/koncepce.md](docs/koncepce.md) | funkční koncepce aplikace |
| [docs/pozadavky.md](docs/pozadavky.md) | funkční a nefunkční požadavky |
| [docs/vyvoj.md](docs/vyvoj.md) | vývojářská příručka: prostředí, spuštění, testy, CI, řešení problémů |
| [docs/konvence.md](docs/konvence.md) | konvence a pravidla implementace |
| [docs/adr/](docs/adr/) | architektonická rozhodnutí (ADR) |

## Rychlý start

Požadavky: JDK 25, Node.js 26+, Docker Desktop.

```bash
cd backend && ./mvnw spring-boot:run     # API na http://localhost:8080 (PostgreSQL se spustí automaticky)
cd frontend && npm install && npm run dev  # aplikace na http://localhost:5173
```

Testy: `cd backend && ./mvnw verify`, `cd frontend && npm test`. Podrobnosti v [docs/vyvoj.md](docs/vyvoj.md).
