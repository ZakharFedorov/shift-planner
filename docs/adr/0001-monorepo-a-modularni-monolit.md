# ADR-0001: Monorepozitář a modulární monolit

- **Stav:** přijato
- **Datum:** 12. 11. 2026

## Kontext

Aplikace se vyvíjí v rámci diplomové práce. Skládá se z REST API (Spring Boot) a webového
klienta (React). Rozsah domény (rozpis směn, kontrola omezení pracovní doby, výpočet odměn) a
počet uživatelů (šest bloků recepce) nevyžadují nezávislé škálování ani nasazování částí systému.
Autentizace má používat session cookie (HttpOnly, Secure, SameSite) a ochranu proti CSRF.

## Zvažované varianty

1. **Jeden repozitář (`backend/` + `frontend/`)**: změna API a UI v jednom commitu, jedna historie a jedna CI.
2. **Dva repozitáře**: nezávislé verze, ale riziko nesouladu kontraktu API a více režie.
3. **Frontend zabalený do JAR aplikace Spring Boot**: jeden artefakt, ale svázané sestavení a horší vývoj s Vite.
4. **Mikroslužby**: pro daný rozsah zbytečná provozní složitost (síťová komunikace, distribuované transakce).

## Rozhodnutí

- Jeden repozitář se složkami `backend/` a `frontend/`.
- Backend je **modulární monolit**. Moduly jsou balíčky podle funkcí (`auth`, `team`, `schedule`,
  `constraints`, `payroll`, `calendar`, `audit`) s vnitřními vrstvami `api / application / domain / infrastructure`.
  Sdílené technické prvky jsou v modulu `shared`.
- Hranice modulů ověřuje test `ModularityTests` (Spring Modulith). Za běhu se Spring Modulith nepoužívá.
- V provozu jsou SPA i `/api` dostupné pod **jedním originem** přes reverzní proxy s HTTPS.
  Ve vývoji tutéž roli plní proxy dev serveru Vite.
- Schéma databáze se mění výhradně migracemi Flyway (`ddl-auto=validate`).

## Důsledky

- Session cookie může mít `SameSite=Strict` a není potřeba konfigurovat CORS.
- Porušení hranic mezi moduly odhalí test už v CI.
- Pokud by se v budoucnu některý modul (např. automatické plánování) osamostatnil, lze ho
  vyjmout podél existující hranice modulu.
