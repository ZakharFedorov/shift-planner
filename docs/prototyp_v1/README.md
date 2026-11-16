# Prototyp uživatelského rozhraní – verze 1

Prototyp aplikace Shift planner vytvořený v nástroji Claude Design. Slouží jako podklad pro implementaci frontendu
a ověření pravidel z [koncepce](../koncepce.md).

## Obsah složky

| Soubor | Popis |
|---|---|
| `01-…07-*.png` | Snímky jednotlivých obrazovek |
| `prototyp_v1-vsechny-obrazovky.pdf` | Všechny obrazovky v jednom dokumentu |
| `prototyp_v1-vsechny-obrazovky.html` | Všechny obrazovky v jednom souboru, otevírá se přímo v prohlížeči |

## Obrazovky

| Č. | Obrazovka | Role |
|---|---|---|
| 01 | Rozpis směn | zaměstnanec |
| 02 | Rozpis směn | administrátor |
| 03 | Tým | všichni |
| 04 | Můj profil | všichni |
| 05 | Nastavení bloku | administrátor |
| 06 | Přihlášení | – |
| 07 | Registrace | – |

## Vizuální styl

- Navigace: TUL Mono 13 px, verzálky, prostrkání 1 px, bílá na `#0076D5`, aktivní položka podtržená `#A3D2FF`. Ostatní text: Inter.
- Typy směn: R – ranní `#F2B544`, O – odpolední `#4C9FE6`, N – noční `#1E2A5A`.
- Svátek: číslo dne červeně `#C62828`. Dnešní den: tmavý kruh `#1A2233`. Řádek přihlášeného uživatele: `#DAE9F9`.

## Pravidla znázorněná v prototypu

- **Placené hodiny:** neplacené přestávky 11:30–12:00, 18:00–18:30 a 02:00–02:30; směna, která do nich zasahuje, se o ně krátí. Počítá se po 30 minutách.
- **Přiřazení zaměstnance** (administrátor, okno „Upravit“): kontrola překryvu směn, odpočinku 11 h mezi směnami a měsíčního limitu hodin. Vedoucí recepce nemá limit.
- **Dělená směna (záskok):** u směny o více částech lze vybrat část, které se změna týká.
- **Pevná směna** se nedá upravit přímo; nejprve se uvolní.
- **Stav rozpisu:** *Uložit* ukládá rozpracovanou verzi, *Publikovat* ji zveřejní zaměstnancům (zobrazuje se datum zveřejnění). Nezveřejněné změny jsou označeny upozorněním.
- **Odevzdání hodin:** *Odevzdat* (po potvrzení) uzamkne rozpis daného měsíce; nelze, dokud existují nezveřejněné změny.
- **Nastavení:** měsíce se přepínají společně pro všechny sekce; převod administrátorských práv vyžaduje potvrzení.

Závazná pravidla aplikace definuje koncepce (prototyp znázorňuje jen jejich část).

## Ukázková data

Listopad 2026, blok A, sazba 165 Kč/h. 17. 11. svátek, 18. 11. dělená směna (záskok), 19. 11. částečně obsazený den.
