# Vývojářská příručka

Sestavení, spuštění a testování aplikace. Architektonická rozhodnutí jsou v [adr/](adr/),
pravidla implementace v [konvence.md](konvence.md).

## Struktura repozitáře

| Složka | Obsah |
|---|---|
| `backend/` | REST API: Java 25, Spring Boot 4.1, Maven, balíček `cz.tul.shiftplanner` |
| `frontend/` | SPA: React 19, TypeScript 6, Vite 8, React Router 8, TanStack Query, i18next |
| `docs/` | dokumentace |
| `.github/workflows/` | CI (GitHub Actions) |

## Požadavky

- JDK 25; proměnná prostředí `JAVA_HOME` musí ukazovat na JDK 25
- Node.js 26 nebo novější
- Docker (vývojová databáze a integrační testy)

Maven není nutné instalovat, součástí repozitáře je Maven Wrapper (Maven 3.9). Použitou verzi
Mavenu i JDK vypíše `./mvnw -v` ve složce `backend/`.

## Spuštění

**Backend** (ve složce `backend/`):

```bash
./mvnw spring-boot:run
```

PostgreSQL 18 z `backend/compose.yaml` se spustí automaticky. API běží na
<http://localhost:8080>, stav aplikace na <http://localhost:8080/actuator/health>.
Bez Docker Compose lze spustit třídu `TestShiftPlannerApplication`, databázi pak poskytne
Testcontainers.

**Frontend** (ve složce `frontend/`):

```bash
npm install
npm run dev
```

Aplikace běží na <http://localhost:5173>. Požadavky na `/api` a `/actuator` přeposílá dev server
na backend, takže SPA i API sdílejí jeden origin (ADR-0001).

## Testy a kontroly kvality

```bash
cd backend && ./mvnw verify        # unit a integrační testy (vyžaduje Docker)
cd frontend && npm test            # Vitest + React Testing Library
cd frontend && npm run lint        # ESLint
cd frontend && npm run typecheck   # TypeScript
cd frontend && npm run build       # produkční sestavení
```

Test `ModularityTests` ověřuje hranice modulů a generuje jejich diagramy do
`backend/target/spring-modulith-docs`.

## CI

Workflow `.github/workflows/ci.yml` běží při push do `main` a u pull requestů: backend
`./mvnw verify`, frontend `npm ci`, lint, typecheck, testy a build.

## Závislosti

- Verze platformy a důvody jejich volby: [ADR-0002](adr/0002-verze-platformy.md).
- Zranitelnosti závislostí frontendu se kontrolují příkazem `npm audit`.
