# ADR-0004: Reprezentace času

- **Stav:** přijato
- **Datum:** 16. 11. 2026

## Kontext

Směny jsou definovány v místním čase recepce (`Europe/Prague`) a běžně přecházejí přes půlnoc,
hranici týdne i měsíce. V noci přechodu na letní nebo zimní čas trvá noční směna 22:00–06:00
skutečně 7 nebo 9 hodin. Kontroly odpočinku musí vycházet ze skutečně uplynulé doby, výpočet
placené doby naopak z času na hodinách (koncepce, kap. 8 a 10).

## Zvažované varianty

1. **Místní datum a čas bez zóny** (`LocalDateTime`, `timestamp`): jednoduché, ale v noci přechodu
   je hodnota nejednoznačná a rozdíl dvou časů neodpovídá skutečně uplynulé době.
2. **Okamžiky** (`Instant`, `timestamptz`) **a převod přes pracovní zónu**: jednoznačné hodnoty
   a správné trvání; místní datum a čas se odvozují výpočtem.
3. **Okamžiky i místní časy uložené vedle sebe**: snadné čtení místních hodnot, ale riziko
   nesouladu obou údajů.

## Rozhodnutí

- Časové okamžiky se v kódu reprezentují jako `Instant`, v databázi jako `timestamptz` v UTC
  (varianta 2).
- Intervaly jsou polootevřené `[začátek, konec)`.
- Pracovní zóna je jediná konstanta `BusinessTime.ZONE` (`Europe/Prague`).
- Začátek a konec směny se získávají převodem místního data a času v pracovní zóně na okamžik,
  nikdy přičtením hodin k jinému času.
- Kontroly omezení (odpočinek, překryvy, délka pracovního období) pracují s okamžiky. Placená doba
  se počítá jako rozdíl místních časů začátku a konce.
- Aktuální čas se získává pouze z beanu `Clock`.
- Státní svátky ČR jsou data vázaná na konkrétní rok.

## Důsledky

- Přechod na letní nebo zimní čas mění skutečnou délku směny pro kontrolu omezení, ale nemění
  placenou dobu.
- Kalendářní pojmy (den, týden, měsíc, víkend, svátek) se vyhodnocují vždy v pracovní zóně,
  nezávisle na časové zóně serveru a databáze.
- Čas lze v testech pevně nastavit náhradou beanu `Clock`.
- Místní čas uvnitř hodiny přechodu (02:00–03:00) při přechodu na letní čas neexistuje a při
  přechodu na zimní čas nastává dvakrát. Pravidlo pro začátek nebo konec přiřazení v této hodině
  se určí při návrhu modulu `schedule`.
