# ADR-0002: Verze platformy

- **Stav:** přijato
- **Datum:** 12. 11. 2026

## Kontext

Původně plánovaný stack počítal se Spring Boot 3 a Java 21. Řada Spring Boot 3.5 ukončila
open-source podporu 30. 6. 2026, takže pro ni již nevycházejí veřejné bezpečnostní opravy.
Zadání práce klade důraz na zabezpečení dat a komunikace.

## Zvažované varianty

1. Spring Boot 3.5 + Java 21: více návodů, ale bez veřejných bezpečnostních oprav.
2. Spring Boot 4.1 + Java 25 LTS: podporovaná řada, Spring tým doporučuje JDK 25.

## Rozhodnutí

| Komponenta | Verze |
|---|---|
| Java | 25 (LTS) |
| Spring Boot | 4.1.x (přechod na 4.2 po vydání je minor upgrade) |
| PostgreSQL | 18 |
| Node.js | 26 (LTS) |
| Build | Maven 3.9 (wrapper v repozitáři) |
| Frontend | React 19, React Router 8, Vite 8, Vitest 5, ESLint 10 |
| TypeScript | 6.0 (ne 7.0: typescript-eslint zatím TypeScript 7 nepodporuje) |

## Důsledky

- Spring Boot 4 = Jakarta EE 11, Jackson 3 a modulární startery (`spring-boot-starter-webmvc` apod.).
  Návody pro Spring Boot 3 nelze přebírat bez úprav.
- Testcontainers i vývojová databáze používají stejnou hlavní verzi PostgreSQL jako provoz.
- Závislosti frontendu se kontrolují příkazem `npm audit`. Tranzitivní závislost `minimatch`
  (používají ji ESLint a typescript-eslint, pouze při lintování) je přes `overrides` v `package.json`
  vynucena na opravenou verzi 11.0.1+ (GHSA-g3hv-75wg-m249). Override lze odstranit, jakmile
  ESLint i typescript-eslint vydají verze s opravenou závislostí.
