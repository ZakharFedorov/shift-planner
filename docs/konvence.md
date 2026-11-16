# Konvence a pravidla implementace

Závazná pravidla pro návrh a psaní kódu. Odůvodnění hlavních rozhodnutí je v [adr/](adr/).
Postup sestavení a spuštění popisuje [vyvoj.md](vyvoj.md).

## Postup vývoje

1. Nová funkce začíná návrhem: změny doménového modelu, API a přístupových práv, invarianty
   a testovací případy. Implementace následuje až po odsouhlasení návrhu.
2. Implementuje se po vertikálních řezech: migrace → doména → služba → API → UI → testy.
3. Nejprve MVP: ruční plánování, kontrola omezení, správa pracovníků, výpočet mzdy. Rozšíření
   (dostupnost, automatické plánování, spravedlnost) se neimplementují předčasně, ale datový model
   jim nesmí bránit.
4. Žádné předčasné komplikace: modulární monolit; mikroslužby, message brokery apod. jen při
   skutečné potřebě.
5. Významná rozhodnutí se zapisují jako ADR: kontext → varianty → rozhodnutí → důsledky.

## Architektura backendu

- Balíčky podle funkcí (moduly): `auth`, `team`, `schedule`, `constraints`, `payroll`, `calendar`,
  `audit`, sdílený `shared`. Uvnitř modulu vrstvy `api / application / domain / infrastructure`.
- Doménová logika je pokud možno nezávislá na Springu (čisté třídy, snadno testovatelné).
- Modul smí používat jen veřejné API jiného modulu; hranice hlídá `ModularityTests`.
- DTO jsou oddělená od JPA entit.

## REST API a chyby

- Všechny chyby mají formát RFC 7807 (Problem Details) se strojově čitelnou vlastností `code`
  (konstanty v `ProblemCodes`). Pole `detail` obsahuje srozumitelnou zprávu v češtině.
- Zamítnutí kvůli omezení pracovní doby vrací specifický kód (např. pro nedodržení odpočinku)
  a českou zprávu s důvodem.
- Klientovi se nikdy nevrací interní podrobnosti (výpis zásobníku, text výjimky).
- Nepřihlášený uživatel dostává 401, přihlášený bez oprávnění 403.

## Databáze

- PostgreSQL. Schéma se mění **výhradně migracemi Flyway** (`backend/src/main/resources/db/migration`);
  Hibernate schéma jen validuje (`ddl-auto=validate`).
- Časové okamžiky: `timestamptz`; peněžní částky a sazby: `numeric`.
- Jeden administrátor na blok je zajištěn na úrovni databáze (např. částečný unikátní index)
  a transakcí. Souběžné úpravy rozpisu chrání optimistické zamykání.

## Práce s časem

Odůvodnění: [ADR-0004](adr/0004-reprezentace-casu.md).

- Pracovní časová zóna je `Europe/Prague` (`BusinessTime.ZONE`), včetně přechodu na letní a zimní
  čas: noční směna může trvat 7 nebo 9 hodin.
- Okamžiky se v kódu reprezentují jako `Instant`, v databázi jako `timestamptz` (UTC).
- Intervaly jsou polootevřené `[začátek, konec)`.
- Konec směny se nikdy nepočítá přičtením hodin k lokálnímu času („+8 hodin“); vždy se převádí
  lokální datum a čas v zóně `Europe/Prague` na `Instant`.
- Kontroly omezení (odpočinek, překryvy) pracují se skutečnými okamžiky. Placená doba se naopak
  počítá podle místního času na hodinách, takže přechod na letní či zimní čas mzdu nemění
  (koncepce, kap. 10).
- Aktuální čas se získává jen z beanu `Clock`, nikdy přímo `Instant.now()` / `LocalDate.now()`.
- Státní svátky ČR jsou data vázaná na konkrétní rok.

## Směny a přiřazení

- Požadovaný interval pokrytí (směna) je uložen odděleně od přiřazení pracovníků (částí intervalu).
- Model musí podporovat částečné pokrytí a záskoky, dodatečné intervaly, přiřazení napříč bloky
  a pevné ranní směny vedoucí recepce (s výjimkami a s ohledem na svátky).

## Kontrola omezení

- Omezení se kontrolují **pouze na serveru**, přes všechny bloky pracovníka a přes hranice týdnů
  a měsíců. Administrátor je nemůže obejít.
- Validátor omezení je čistá doménová logika, pokrytá testy.
- Konkrétní omezení (délka souvislého pracovního období, denní a týdenní odpočinek, překryvy,
  měsíční limit) definuje koncepce, kap. 8; tvrzení o zákoníku práce se vždy ověřují v aktuálním
  znění zákona.
- Kontrola zohledňuje všechna uložená přiřazení pracovníka, zveřejněná i nezveřejněná (koncepce,
  kap. 7).

## Mzdy

- Peněžní hodnoty výhradně `BigDecimal` / `numeric`, nikdy `float` / `double`.
- Sazby a parametry odměňování se ukládají s obdobím platnosti.
- Příplatky jedné noční směny mohou patřit do různých zúčtovacích měsíců (pravidlo z koncepce).
- Pravidla, která se mohou lišit podle praxe recepce (30minutová přestávka, základní mzda na
  hranici měsíců, souběh příplatků), jsou konfigurovatelná.

## Bezpečnost

- Vlastní autentizace: hash hesla Argon2id, session v cookie `HttpOnly`, `Secure`,
  `SameSite=Strict`, ochrana proti CSRF, omezení počtu pokusů o přihlášení. Autentizace je oddělena
  od autorizace, aby bylo možné později doplnit přihlášení přes TUL bez změny autorizace
  ([ADR-0003](adr/0003-autentizace-a-relace.md)).
- Autorizace na serveru v každém požadavku: role + blok + vlastnictví zdroje. Kontroly jen ve
  frontendu se nepočítají.
- Mzdové údaje vidí jen sám pracovník a administrátor jeho bloku.
- Změny přiřazení se zapisují do auditního záznamu (kdo, kdy, co).
- Platí zásady OWASP Top 10: validace všech vstupů, žádná tajemství v kódu.

## Frontend

- Texty UI výhradně česky v `frontend/src/i18n/locales/cs.json`; v komponentách žádné pevně
  zapsané texty. Klíče jsou typově kontrolované.
- Chyby API se zobrazují podle `code` (překlad v sekci `errors`), `detail` slouží jako záloha.
- Data ze serveru se načítají přes TanStack Query; klient API (`src/api/http.ts`) převádí chybové
  odpovědi na `ApiError`.
- Responzivní rozvržení pro počítač i mobil.

## Testy

- Významná logika je pokryta testy: JUnit 5 + AssertJ, integrační testy s Testcontainers
  (PostgreSQL), Vitest + React Testing Library, později Playwright pro end-to-end.
- Pro omezení a mzdy se před implementací sestaví tabulka testovacích případů. Povinné hraniční
  případy: půlnoc, hranice měsíce, přechod letního/zimního času, svátek o víkendu, přiřazení napříč
  bloky, částečný záskok.

## Jazyk

- Identifikátory a komentáře v kódu: angličtina.
- Texty uživatelského rozhraní a chybové zprávy pro uživatele: čeština.
- Dokumentace projektu: čeština.
