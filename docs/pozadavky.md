# Požadavky na aplikaci

Funkční a nefunkční požadavky odvozené ze [zadání](zadani.md) a z [koncepce](koncepce.md).
Koncepce popisuje chování podrobně. Tento dokument požadavky vyjmenovává, čísluje a určuje jejich
prioritu.

## Značení

- `FR-nn` je funkční požadavek, `NFR-nn` nefunkční požadavek. Čísla se po přidělení nemění.
- Sloupec **Zdroj** odkazuje na kapitolu koncepce (K) nebo bod zadání (Z).
- Sloupec **Priorita**: **M** – první verze aplikace, **R** – rozšíření po dokončení první verze.

## Aktéři

| Aktér | Popis |
|---|---|
| Pracovník | Člen týmu recepce jednoho bloku. |
| Administrátor bloku | Pracovník, který spravuje rozpis a nastavení svého bloku. V bloku je nejvýše jeden. |
| Vedoucí recepce | Člen týmu, který vykonává pevné ranní směny. V bloku je nejvýše jedna. |

## Funkční požadavky

### Účty a přihlášení

| ID | Požadavek | Zdroj | Priorita |
|---|---|---|---|
| FR-01 | Uživatel se zaregistruje zadáním jména, příjmení, e-mailu, hesla (dvakrát), bloku A–F a zařazení (pracovník, nebo vedoucí recepce). | K 3.1 | M |
| FR-02 | Uživatel se přihlásí e-mailem a heslem a může se odhlásit. Bez přihlášení nejsou data dostupná. | K 3.1 | M |
| FR-03 | Je-li v bloku vedoucí recepce již zaregistrována, registraci další vedoucí téhož bloku systém zamítne. | K 2.3 | M |
| FR-04 | Uživatel se přihlásí univerzitním účtem TUL. | K 3.1, Z 5 | R |

### Tým a role

| ID | Požadavek                                                                                                                                              | Zdroj | Priorita |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------|---|---|
| FR-05 | Každý uživatel patří právě do jednoho bloku.                                                                                                           | K 2.1 | M |
| FR-06 | Nemá-li blok administrátora, může se jím stát kterýkoli pracovník bloku (práva získá první, kdo funkci úspěšně použije).                               | K 2.2 | M |
| FR-07 | Administrátor předá svá práva jinému pracovníkovi týmu. Předání vyžaduje potvrzení a předchozí administrátor se stává běžným pracovníkem.              | K 2.2, 3.6 | M |
| FR-08 | Stránka týmu zobrazuje pro vybraný měsíc souhrn za blok a seznam členů: role, počet směn, přiřazené hodiny, limit, zbývající hodiny a vytížení limitu. | K 3.4 | M |
| FR-09 | Stránka týmu uvádí pracovníky jiných bloků, kteří mají ve vybraném měsíci přiřazení v tomto bloku.                                                     | K 3.4 | M |
| FR-10 | Administrátor nastavuje každému pracovníkovi měsíční limit hodin pro každý měsíc zvlášť. Pracovníci bez limitu pro vybraný měsíc jsou zvýrazněni.      | K 3.4, 3.6 | M |
| FR-11 | Administrátor mění hodinovou sazbu pracovníka s platností od vybraného měsíce. Sazba se přenáší do dalších měsíců až do další změny.                   | K 3.6 | M |

### Zobrazení rozpisu

| ID | Požadavek | Zdroj | Priorita |
|---|---|---|---|
| FR-12 | Měsíční kalendář bloku zobrazuje směny, přiřazené pracovníky, stav obsazení, státní svátky, pevné směny, přiřazení z jiných bloků a vlastní směny uživatele. | K 3.2 | M |
| FR-13 | Detail dne zobrazuje směny a dodatečné intervaly dne, jejich stav, jednotlivá přiřazení s časy a neobsazené části. | K 3.2 | M |
| FR-14 | Týdenní tabulka zobrazuje přiřazení po pracovnících a dnech, pokrytí každé směny a umožňuje přechod mezi týdny. Administrátor vidí navíc hodiny a limity pracovníků. | K 3.2 | M |
| FR-15 | Pracovník vidí přehled svých směn ve vybraném měsíci: počet směn, přiřazené hodiny, limit a zbývající hodiny. | K 3.2 | M |
| FR-16 | Pracovník vidí své směny včetně směn v jiných blocích. | K 2.1, 2.2 | M |
| FR-17 | Pracovník vidí pouze zveřejněnou verzi rozpisu. | K 7 | M |

### Směny a přiřazení

| ID | Požadavek | Zdroj | Priorita |
|---|---|---|---|
| FR-18 | Každý den má tři standardní směny: ranní 06:00–14:00, odpolední 14:00–22:00 a noční 22:00–06:00. | K 4 | M |
| FR-19 | Administrátor přidává a odebírá dodatečné pracovní intervaly pro konkrétní den. | K 3.6, 4 | M |
| FR-20 | Administrátor přiřadí pracovníka na celou směnu nebo na její část. Začátky a konce připadají na celou nebo půl hodinu. | K 4, 7 | M |
| FR-21 | Administrátor mění a maže přiřazení. | K 3.2 | M |
| FR-22 | Směnu lze rozdělit mezi více pracovníků (záskok). Neobsazená část směny se zobrazuje jako volná. | K 4, 7 | M |
| FR-23 | Administrátor přiřadí na směnu svého bloku pracovníka jiného bloku. Příslušnost pracovníka k domovskému bloku se nemění. | K 2.1 | M |
| FR-24 | Administrátor může vedoucí recepce přiřadit na směnu stejně jako ostatní pracovníky. | K 2.3 | M |

### Pevné směny

| ID | Požadavek | Zdroj | Priorita |
|---|---|---|---|
| FR-25 | Ranní směny od pondělí do pátku jsou automaticky obsazeny vedoucí recepce bloku. Pevné směny lze pro blok zapnout nebo vypnout. | K 5 | M |
| FR-26 | Připadne-li na pracovní den státní svátek, ranní směna je volná k přiřazení. | K 5 | M |
| FR-27 | Administrátor vytváří výjimky pro jednotlivá data a může pevnou směnu uvolnit v detailu dne. Pevnou směnu nelze upravit bez uvolnění. | K 5 | M |

### Stav rozpisu

| ID | Požadavek | Zdroj | Priorita |
|---|---|---|---|
| FR-28 | Administrátor ukládá změny do pracovní verze rozpisu, kterou pracovníci nevidí. | K 7 | M |
| FR-29 | Administrátor rozpis zveřejní. Zobrazuje se datum a čas posledního zveřejnění a upozornění na nezveřejněné změny. | K 7 | M |
| FR-30 | Administrátor rozpis měsíce po potvrzení odevzdá. Odevzdat nelze, dokud existují nezveřejněné změny. | K 7 | M |
| FR-31 | Odevzdaný rozpis je uzamčen: přiřazení, intervaly, limity ani sazby daného měsíce nelze měnit. | K 7 | M |

### Kontrola omezení

| ID | Požadavek                                                                                                                                                                                                      | Zdroj | Priorita |
|---|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---|---|
| FR-32 | Pracovníka nelze přiřadit na překrývající se intervaly.                                                                                                                                                        | K 8 | M |
| FR-33 | Navazující přiřazení tvoří souvislé pracovní období, které nesmí přesáhnout 12 hodin.                                                                                                                          | K 8 | M |
| FR-34 | Mezi dvěma souvislými pracovními obdobími musí být nepřetržitý odpočinek nejméně 11 hodin.                                                                                                                     | K 8 | M |
| FR-35 | V každém kalendářním týdnu musí mít pracovník nepřetržitý odpočinek nejméně 35 hodin.                                                                                                                          | K 8 | M |
| FR-36 | Nelze překročit měsíční limit hodin. Bez nastaveného limitu nelze pracovníka přiřadit. Do limitu se počítá placená doba a přiřazení patří do měsíce, ve kterém začíná. Limit se nevztahuje na vedoucí recepce. | K 3.4, 8 | M |
| FR-37 | Kontroly zohledňují všechna uložená přiřazení pracovníka ve všech blocích, zveřejněná i nezveřejněná, a přiřazení na hranicích týdnů a měsíců.                                                                 | K 7, 8 | M |
| FR-38 | Nepřípustné přiřazení systém zamítne a sdělí důvod. Administrátor nemůže omezení obejít.                                                                                                                       | K 7, 8 | M |

### Výpočet mzdy

| ID | Požadavek | Zdroj | Priorita |
|---|---|---|---|
| FR-39 | Placená doba přiřazení se počítá podle místního času a krátí se o neplacené přestávky, které přiřazení obsahuje. | K 10 | M |
| FR-40 | Základní mzda je součin placené doby a hodinové sazby platné v daném měsíci. | K 10 | M |
| FR-41 | Systém počítá příplatky za noční práci, práci o víkendu a práci ve státní svátek podle parametrů odměňování s obdobím platnosti. | K 10 | M |
| FR-42 | U noční směny na hranici měsíců patří základní mzda a příplatek za noční práci do měsíce začátku směny, příplatky za víkend a svátek do měsíce následujícího. | K 10 | M |
| FR-43 | Profil pracovníka zobrazuje jeho údaje, statistiku hodin, podrobný výpočet odměny a seznam směn za vybraný měsíc. | K 3.5 | M |
| FR-44 | Administrátor prohlíží hodiny a výpočty mezd pracovníků svého bloku. | K 2.2, 10 | M |
| FR-45 | Výpočet vychází ze zveřejněné verze rozpisu a po změně rozpisu se přepočítá. | K 7, 10 | M |
| FR-46 | Státní svátky ČR jsou v systému vedeny jako data pro konkrétní rok. | K 3.2, 10 | M |
| FR-47 | Administrátor exportuje podklady pro zpracování mezd. | K 10 | R |

### Historie změn

| ID | Požadavek | Zdroj | Priorita |
|---|---|---|---|
| FR-48 | Systém zaznamenává, kdo a kdy změnil přiřazení pracovníka a co bylo změněno. | K 3.6, 7 | M |

### Rozšíření

| ID | Požadavek | Zdroj | Priorita |
|---|---|---|---|
| FR-49 | Pracovník zadává směny, které je ochoten odpracovat. | K 3.3 | R |
| FR-50 | Administrátor spustí automatické sestavení rozpisu, které dodrží všechna omezení, a výsledek může ručně upravit. | K 6 | R |
| FR-51 | Automatické sestavení zohledňuje spravedlivé rozdělení zátěže a hodin ve státní svátky. | K 9 | R |
| FR-52 | Pracovník navrhne záskok nebo výměnu směny. | K 2.2 | R |

## Nefunkční požadavky

### Bezpečnost

| ID | Požadavek                                                                                                                                        | Zdroj | Priorita |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------|---|---|
| NFR-01 | Hesla jsou uložena pouze jako hash Argon2id.                                                                                                     | K 11, ADR-0003 | M |
| NFR-02 | Veškerá komunikace mezi prohlížečem a serverem probíhá přes HTTPS.                                                                               | K 11, Z 6 | M |
| NFR-03 | Relace je vedena na serveru. Cookie relace má příznaky `HttpOnly`, `Secure` a `SameSite=Strict`. Požadavky měnící stav jsou chráněny proti CSRF. | K 11, ADR-0003 | M |
| NFR-04 | Počet neúspěšných pokusů o přihlášení je omezen.                                                                                                 | ADR-0003 | M |
| NFR-05 | Oprávnění se ověřuje na serveru u každého požadavku podle role, bloku a vlastnictví zdroje.                                                      | K 11 | M |
| NFR-06 | Mzdové údaje pracovníka vidí pouze on sám a administrátor jeho bloku.                                                                            | K 11 | M |
| NFR-07 | Administrátor mění pouze údaje svého bloku. Rozpisy a nastavení jiných bloků měnit nemůže.                                                       | K 2.1, 11 | M |
| NFR-08 | Všechny vstupy se validují na serveru. Chybové odpovědi neobsahují interní podrobnosti.                                                          | Z 6 | M |
| NFR-09 | Data jsou pravidelně zálohována.                                                                                                                 | K 11 | M |

### Správnost a souběh

| ID | Požadavek                                                                                                       | Zdroj | Priorita |
|---|-----------------------------------------------------------------------------------------------------------------|---|---|
| NFR-10 | Nejvýše jeden administrátor a nejvýše jedna vedoucí recepce na blok jsou zaručeni i při souběžných požadavcích. | K 2.2, 2.3 | M |
| NFR-11 | Souběžná úprava téhož rozpisu nesmí vést ke ztrátě změn. Konflikt se uživateli oznámí.                          | K 7 | M |
| NFR-12 | Omezení pracovní doby se kontrolují výhradně na serveru.                                                        | K 8 | M |
| NFR-13 | Práce s časem zohledňuje časovou zónu `Europe/Prague` včetně přechodu na letní a zimní čas.                     | K 8, 10, ADR-0004 | M |
| NFR-14 | Peněžní částky se počítají v přesné desetinné aritmetice.                                                       | K 10 | M |

### Použitelnost

| ID | Požadavek | Zdroj | Priorita |
|---|---|---|---|
| NFR-15 | Aplikace je použitelná ve webovém prohlížeči na počítači i na mobilním zařízení. | K 1, 12 | M |
| NFR-16 | Jazykem uživatelského rozhraní je čeština. | K 12 | M |
| NFR-17 | Zamítnutí operace obsahuje srozumitelný důvod v češtině. | K 7, 12 | M |
| NFR-18 | Funkce nedostupné pro roli uživatele se v rozhraní nenabízejí. | K 12 | M |

### Ověření

| ID | Požadavek | Zdroj | Priorita |
|---|---|---|---|
| NFR-19 | Kontrola omezení a výpočet mzdy jsou pokryty automatickými testy včetně hraničních případů: půlnoc, hranice měsíce, přechod času, svátek o víkendu, přiřazení napříč bloky, částečný záskok. | K 13 | M |
| NFR-20 | Aplikace je dána k dispozici pracovníkům recepce k testovacímu provozu. | Z 7, K 13 | M |
