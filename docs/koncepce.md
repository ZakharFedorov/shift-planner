# Koncepce webové aplikace pro plánování směn a výpočet odměn pracovníků recepce kolejí TUL

## 1. Cíl projektu

Cílem diplomové práce je vývoj webové aplikace pro plánování pracovních směn pracovníků recepce
kolejí Technické univerzity v Liberci (TUL) a automatický výpočet jejich odměn.

Aplikace má zjednodušit stávající způsob sestavování rozpisu, snížit počet chyb při rozdělování
směn, zajistit dodržování omezení pracovní doby a automatizovat výpočet mzdy.

Hlavními funkcemi aplikace budou ruční sestavování rozpisu, kontrola omezení, správa pracovníků
a výpočet odměn.

V budoucnu se počítá s možností, aby pracovníci zadávali směny, které jsou ochotni odpracovat,
a s automatickým sestavením rozpisu s ohledem na omezení a spravedlivé rozdělení práce.

Aplikace má podporovat provoz šesti bloků kolejí a být dostupná přes webový prohlížeč na počítači
i na mobilních zařízeních.

## 2. Organizace systému

### 2.1. Rozdělení podle bloků kolejí

Systém bude podporovat šest bloků kolejí: A, B, C, D, E a F.

Při registraci uživatel uvede blok, ve kterém pracuje. Každý pracovník může patřit pouze do
jednoho týmu.

Každý blok bude mít vlastní rozpis a vlastní seznam pracovníků.

Systém přitom musí podporovat dočasné přiřazení pracovníků z jiných bloků k pokrytí jednotlivých
směn.

Administrátor může vybrat pracovníka z jiného bloku a přiřadit ho na konkrétní směnu. Toto
přiřazení nemění příslušnost pracovníka k jeho domovskému týmu.

Například pracovník bloku A může být přiřazen na směnu v bloku B. V takovém případě:

- Směna se zobrazuje v rozpisu bloku B.
- Pracovník zůstává členem týmu bloku A.
- Směna se započítává do osobního rozpisu pracovníka.
- Odpracované hodiny se započítávají do jeho individuálního limitu.
- Při kontrole odpočinku se berou v úvahu všechny směny bez ohledu na blok.
- Odměna za směnu se započítává do jeho osobního výpočtu mzdy.

Administrátor jednoho bloku nesmí získat možnost upravovat osobní nastavení pracovníků jiných
bloků.

### 2.2. Uživatelé a administrátorská práva

V aplikaci budou existovat dva typy uživatelů.

**Pracovník (Zaměstnanec)**

Může:

- Prohlížet rozpis svého týmu.
- Prohlížet vlastní směny, včetně směn v jiných blocích.
- Prohlížet seznam kolegů ze svého bloku.
- Prohlížet svůj profil.
- Prohlížet počet přiřazených hodin a vypočtenou mzdu.
- Navrhnout záskok nebo výměnu směny, pokud bude tato možnost implementována.
- Získat administrátorská práva, pokud v týmu chybí administrátor.

V budoucnu bude pracovník moci také zadávat směny, které je ochoten odpracovat.

**Administrátor bloku (Administrátor)**

Má všechny možnosti běžného pracovníka a navíc může:

- Přiřazovat pracovníky na směny.
- Měnit a mazat přiřazení.
- Přidávat potřebné pracovní intervaly.
- Přiřazovat pracovníky z jiných bloků.
- Spravovat individuální limity pracovní doby.
- Měnit potřebné údaje pracovníků svého týmu.
- Vytvářet a zveřejňovat rozpis.
- Prohlížet odpracované hodiny a výpočty mezd pracovníků svého týmu.
- Předat administrátorská práva jinému pracovníkovi.

V každém týmu může být pouze jeden administrátor.

**Získání administrátorských práv**

Pokud v bloku ještě není administrátor, bude pracovníkovi na stránce týmu k dispozici tlačítko
„Stát se administrátorem“.

Administrátorská práva získá první pracovník bloku, který tuto funkci úspěšně použije.

Pokud administrátor již existuje, nelze tato práva získat samostatně.

Stávající administrátor může svá oprávnění předat jinému pracovníkovi týmu. Po předání se
předchozí administrátor stává běžným pracovníkem.

Kontrola existence administrátora a předání práv se musí provádět na serveru, aby se vyloučilo
současné jmenování dvou administrátorů.

V první verzi se příslušnost uživatele k bloku neověřuje, uživatel ji uvádí při registraci.

Samostatná role systémového administrátora se v aplikaci nepředpokládá.

### 2.3. Vedoucí recepce

Každý blok má jednu vedoucí (jednoho vedoucího) recepce. Vedoucí je členem týmu svého bloku
a v rozpisu, v týdenní tabulce i na stránce týmu se zobrazuje s označením „Vedoucí recepce“.

Své zařazení uvádí vedoucí sama při registraci (kap. 3.1); v aplikaci ji nikdo nejmenuje. Je-li
v bloku vedoucí již zaregistrována, registraci další vedoucí téhož bloku systém zamítne.

V první verzi se zařazení, stejně jako příslušnost k bloku, neověřuje. Ověření se bude řešit
společně s přihlášením přes univerzitní účet TUL (kap. 3.1).

Vedoucí vykonává pevné ranní směny (kap. 5). Administrátor ji může přiřadit i na jinou směnu
stejně jako ostatní pracovníky.

Rozsah práce vedoucí je dán její dohodou se zaměstnavatelem. Aplikace jej neeviduje ani
nekontroluje: vedoucí nemá měsíční limit hodin, nenastavuje se jí hodinová sazba a její odměna se
nepočítá. Počet přiřazených hodin se u ní zobrazuje pouze informativně. Ostatní omezení pracovní
doby (kap. 8) platí i pro vedoucí.

### 2.4. Pracovněprávní vztah

Pracovníci recepce pracují na základě dohody o pracovní činnosti (DPČ), vedoucí recepce na základě
dohody o provedení práce (DPP). Pravidla pracovní doby a odpočinku uvedená v kap. 8 platí pro oba
typy dohod.

## 3. Hlavní stránky aplikace

### 3.1. Registrace a přihlášení (Registrace / Přihlášení)

V první fázi bude aplikace používat vlastní systém registrace a přihlášení.

Při registraci uživatel zadá:

- Jméno.
- Příjmení.
- E-mail.
- Heslo (zadává se dvakrát pro kontrolu).
- Blok kolejí (A–F).
- Zařazení: pracovník recepce, nebo vedoucí recepce (kap. 2.3).

Po registraci se uživatel bude moci přihlásit pomocí e-mailu a hesla.

Hesla musí být uložena pouze ve formě bezpečných hashů. Přístup k datům je povolen pouze po
úspěšné autentizaci.

V budoucnu se počítá s integrací s univerzitním autentizačním systémem TUL. Uživatelé se tak
budou moci přihlásit univerzitním účtem.

Konkrétní způsob napojení bude určen po dohodě s IT oddělením TUL.

### 3.2. Hlavní stránka — rozpis směn (Rozpis směn)

Hlavní stránka slouží k prohlížení pracovního rozpisu vlastního týmu.

Bude se skládat ze dvou hlavních částí.

**Horní část — měsíční kalendář**

V kalendáři se zobrazují:

- Všechny dny vybraného měsíce.
- Státní svátky ČR.
- Ranní, odpolední a noční směny.
- Přiřazení pracovníci.
- Neobsazené směny.
- Částečně obsazené směny.
- Dodatečné pracovní intervaly vytvořené administrátorem.
- Pevné směny vedoucí recepce (označené symbolem zámku).
- Přiřazení pracovníků z jiných bloků (označená písmenem domovského bloku).
- Vlastní směny přihlášeného uživatele (zvýrazněné) a dnešní den.

Směny musí mít srozumitelné vizuální označení, které umožní rozlišit obsazené, volné a částečně
obsazené intervaly. Význam barev a značek vysvětluje legenda nad kalendářem. Směna rozdělená mezi
více pracovníků se v kalendáři zobrazuje jménem prvního pracovníka a počtem dalších (například
„Procházková +1“).

V záhlaví stránky se zobrazuje blok, vybraný měsíc a stav rozpisu včetně data a času posledního
zveřejnění (kap. 7).

Po výběru dne se vedle kalendáře zobrazí detail dne: všechny směny a dodatečné intervaly daného
dne, jejich stav (obsazená, rozdělená, částečně obsazená s počtem chybějících hodin, volná),
jednotlivá přiřazení s časy a neobsazené části. Výběr dne zároveň přepne týdenní tabulku na
týden, do kterého den patří.

**Dolní část — týdenní tabulka rozpisu**

Pod kalendářem bude umístěna tabulka pro prohlížení a úpravu rozpisu v rámci jednoho týdne.

Vodorovně jsou dny v týdnu a pracovní směny, svisle pracovníci týmu.

V buňkách tabulky se zobrazují přiřazené pracovní intervaly.

Uživatel se bude moci přesouvat mezi týdny tlačítky „Předchozí týden“ a „Následující týden“.

Tabulka má umožnit prohlížet vytížení jednotlivých pracovníků a zjistit volné směny.

Sloupec každého dne je rozdělen podle typů směn (ranní, odpolední, noční). Přiřazení, které
pokrývá jen část směny (záskok nebo rozdělená směna), je označeno hvězdičkou. Státní svátek
a vybraný den jsou v záhlaví zvýrazněny, stejně jako řádek přihlášeného uživatele. Pracovník
z jiného bloku, který má v daném týdnu přiřazení v tomto bloku, je uveden na samostatném řádku
s označením domovského bloku.

Poslední řádek tabulky ukazuje pokrytí každé směny: plně obsazená, částečně obsazená, nebo volná.

Administrátor vidí navíc sloupec s hodinami přiřazenými každému pracovníkovi ve vybraném měsíci
a s jeho limitem. U vedoucí recepce se zobrazuje jen počet hodin, u pracovníka z jiného bloku jen
hodiny přiřazené v tomto bloku.

**Funkce běžného pracovníka**

Pracovník může:

- Prohlížet rozpis.
- Vybírat měsíc a týden.
- Prohlížet podrobnosti směn.
- Vidět přiřazené pracovní intervaly.
- Vidět nad kalendářem přehled svých směn ve vybraném měsíci: počet směn, přiřazené hodiny, limit
  a zbývající hodiny.
- Navrhnout z detailu dne výměnu směny, pokud bude tato možnost implementována (kap. 2.2).

Pracovník vidí vždy jen zveřejněnou verzi rozpisu (kap. 7).

**Funkce administrátora**

Administrátor navíc může:

- Přiřazovat pracovníky na směny.
- Měnit přiřazení.
- Mazat přiřazení.
- Vybrat pracovníka z jiného bloku.
- Přidávat dodatečné pracovní intervaly.
- Prohlížet hlášení o porušení omezení.
- Uvolnit pevnou směnu vedoucí recepce pro konkrétní den (kap. 5).
- Ukládat koncept rozpisu.
- Zveřejňovat rozpis.
- Odevzdat hodiny za měsíc, a tím rozpis uzamknout.

Přiřazení se upravují v detailu dne tlačítkem „Upravit“ u příslušné směny. U směny rozdělené na
více částí administrátor vybere část, které se změna týká.

Ukládání, zveřejnění a odevzdání rozpisu popisuje kap. 7.

Rozpis může měnit pouze administrátor příslušného bloku.

Při každé změně musí systém kontrolovat povinná omezení pracovní doby.

V budoucnu zde přibude také tlačítko pro automatické rozdělení směn.

### 3.3. Stránka dostupnosti (Moje dostupnost) — budoucí rozšíření

V budoucnu se počítá se samostatnou stránkou, na které bude pracovník označovat směny, které je
ochoten odpracovat.

Hlavním prvkem stránky bude kalendář.

Pro každé datum bude pracovník moci vybrat jednu nebo více směn:

- Ranní — 06:00–14:00.
- Odpolední — 14:00–22:00.
- Noční — 22:00–06:00.

Pracovník tak bude moci například označit, že 15. listopadu je ochoten pracovat na odpolední
nebo noční směně.

Další kategorie preferencí, opakující se šablony dostupnosti ani samostatné označení dnů
nedostupnosti se nepředpokládají.

Zadané údaje budou využity při automatickém rozdělování směn.

V první fázi tato stránka není povinná. Administrátor bude přiřazovat pracovníky ručně.

### 3.4. Stránka pracovníků (Tým)

Stránka zobrazuje seznam všech pracovníků vlastního bloku pro vybraný měsíc.

V horní části je souhrn za blok a měsíc:

- Počet pracovníků bloku.
- Celkový počet přiřazených hodin včetně výpomoci z jiných bloků.
- Počet neobsazených směn a kolik z nich je obsazeno částečně.

Pod souhrnem je uvedeno, kdo je administrátorem bloku.

U každého pracovníka budou k dispozici základní údaje:

- Jméno a příjmení.
- Domovský blok.
- Role: zaměstnanec, administrátor, nebo vedoucí recepce.
- Počet přiřazených směn ve vybraném měsíci.
- Maximální povolený počet pracovních hodin za měsíc.
- Počet již přiřazených hodin ve vybraném měsíci.
- Počet zbývajících hodin do dosažení limitu.
- Vytížení limitu v procentech.

Vedoucí recepce limit nemá (kap. 2.3); zobrazuje se u ní jen počet směn a hodin.

Samostatný seznam „Výpomoc z jiných bloků“ uvádí pracovníky jiných bloků, kteří mají ve vybraném
měsíci přiřazení v tomto bloku: jméno, domovský blok, směny a počet hodin. Tyto hodiny se
započítávají do limitu pracovníka v jeho domovském bloku.

Má-li například pracovník nastaven limit 80 hodin a přiřazeno již 56 hodin, systém ukáže, že lze
přiřadit ještě 24 hodin.

Limit nastavuje administrátor každému pracovníkovi pro každý měsíc zvlášť. Do limitu se započítává
placená doba přiřazení, tedy bez přestávek (kap. 10): noční směna se započítá jako 7,5 hodiny.
Přiřazení přes hranici měsíců se započítá do měsíce, ve kterém začíná.

Pracovníka nelze přiřadit na směnu v měsíci, pro který nemá nastavený limit. Systém takové
přiřazení zamítne s odpovídající zprávou a stránka týmu pracovníky bez limitu pro vybraný měsíc
zvýrazní.

Žádné povinné cílové hodnoty pracovní doby se nepředpokládají.

Administrátor bude moci otevřít podrobné informace o pracovníkovi a změnit jeho individuální limit.

Na stránce bude také možnost získat administrátorská práva, pokud v týmu chybí administrátor,
a předat tato práva stávajícím administrátorem.

### 3.5. Osobní profil (Můj profil)

V osobním profilu si pracovník bude moci prohlížet své údaje a statistiku pracovních hodin.

Stránka bude obsahovat:

- Jméno a příjmení.
- E-mail.
- Pracovní blok.
- Zda má administrátorská práva.
- Hodinovou sazbu.
- Nastavený měsíční limit hodin.
- Počet přiřazených hodin.
- Počet zbývajících hodin do dosažení limitu.
- Počet nočních hodin.
- Počet hodin o víkendech.
- Počet hodin ve státní svátky.
- Vypočtenou mzdu.

Pracovník bude moci vybrat měsíc a zobrazit podrobný výpočet odměny.

Ve výpočtu se samostatně zobrazují:

- Základní mzda.
- Příplatek za noční práci.
- Příplatek za práci o víkendu.
- Příplatek za práci ve svátek.
- Celková částka.

U každé položky je uveden počet hodin, způsob výpočtu (sazba, procento příplatku) a částka.

Pod výpočtem je seznam směn pracovníka ve vybraném měsíci: datum, typ směny, čas, placené hodiny
a příplatky, které za směnu náleží (noc, víkend, svátek) s počtem hodin.

Hodinovou sazbu a limit pracovník nemůže měnit; nastavuje je administrátor bloku.

Výpočet mzdy bude prováděn přímo na základě pracovního rozpisu. Samostatná evidence skutečně
odpracované doby se nepředpokládá.

Pokud pracovník nemohl odpracovat celou přiřazenou směnu nebo došlo k záskoku, administrátor
upraví příslušná přiřazení v rozpisu. Mzda se poté automaticky přepočítá.

### 3.6. Administrátorská nastavení (Nastavení)

Administrátorská nastavení budou dostupná pouze administrátorovi příslušného bloku.

Nastavení se zadává po měsících. Vybraný měsíc je společný pro všechny části stránky: intervaly,
výjimky, limity i sazby.

Hlavní funkce:

**Nastavení pracovních směn**

- Zobrazení standardních typů směn.
- Přidávání dodatečných pracovních intervalů: den vybraného měsíce, začátek a konec.
- Odebrání dodatečného intervalu.
- Změna potřebných pracovních intervalů pro konkrétní data.
- Zadání výjimek ze standardního rozpisu.

**Nastavení pracovníků**

- Nastavení měsíčních limitů pracovních hodin.
- Změna hodinové sazby pracovníka s platností od vybraného měsíce.
- Změna dalších údajů potřebných pro výpočet mzdy.
- Předání administrátorských práv vybranému pracovníkovi týmu; předání vyžaduje potvrzení.

**Pevné směny**

- Zapnutí nebo vypnutí pevných ranních směn pro blok.
- Přehled výjimek ve vybraném měsíci; státní svátky se uvádějí automaticky.
- Vytváření výjimek pro jednotlivá data.

**Historie změn (servisní funkce)**

U důležitých změn rozpisu bude systém automaticky ukládat, kdo a kdy změnil přiřazení pracovníka.
To pomůže vyjasnit sporné situace, například pokud byla směna předána jinému pracovníkovi.

Samostatná stránka pro prohlížení celé historie změn se v první verzi nevyžaduje.

U hodinových sazeb a dalších parametrů odměňování je třeba ukládat období jejich platnosti. To
umožní správně počítat odměny za minulé měsíce i po změně sazeb.

Hodinová sazba pracovníka platí od měsíce, pro který ji administrátor nastavil, až do další
změny; do následujících měsíců se přenáší automaticky. Změna sazby platí od vybraného měsíce
a výpočty za předchozí měsíce neovlivní. Měsíční limit hodin se naopak nepřenáší a nastavuje se
pro každý měsíc zvlášť (kap. 3.4).

## 4. Typy pracovních směn

Systém bude používat tři standardní typy směn:

| Typ směny | Začátek | Konec |
|---|---|---|
| Ranní | 06:00 | 14:00 |
| Odpolední | 14:00 | 22:00 |
| Noční | 22:00 | 06:00 následujícího dne |

Každá směna představuje časový interval, během kterého je nutné zajistit přítomnost pracovníka na
recepci.

Standardní směna je určena pro jednoho pracovníka.

V případě potřeby lze směnu rozdělit mezi více pracovníků.

Například:

- První pracovník pracuje od 14:00 do 19:00.
- Druhý pracovník pracuje od 19:00 do 22:00.

V takovém případě je směna plně obsazena, přestože ji vykonávají dvě osoby.

Administrátor bude moci vytvářet také dodatečné pracovní intervaly s libovolným začátkem a koncem.

Začátek a konec každého pracovního intervalu i každého přiřazení připadají vždy na celou nebo půl
hodinu (například 14:00, 18:30).

Všechna přiřazení se musí zohlednit při kontrole omezení a výpočtu mzdy.

## 5. Pevné pracovní směny

Ranní směny od pondělí do pátku obvykle vykonává vedoucí recepce daného bloku (kap. 2.3).

Takové směny se musí automaticky považovat za obsazené a nezahrnovat se do běžného rozdělování mezi
pracovníky.

Pevné směny lze pro blok v nastavení zapnout nebo vypnout.

Připadne-li na pracovní den státní svátek, ranní směna se stává dostupnou pro přiřazení
pracovníkovi.

Je třeba umožnit přidávání výjimek, například pokud vedoucí chybí nebo se dočasně mění rozvrh
práce.

Pevnou směnu nelze upravit přímo. Administrátor ji nejprve pro daný den uvolní (v detailu dne nebo
výjimkou v nastavení); uvolněnou směnu lze poté obsadit jako běžnou směnu.

Pevné směny se musí zobrazovat v kalendáři i v týdenní tabulce rozpisu.

## 6. Automatické sestavení rozpisu — budoucí rozšíření

Po implementaci ručního řízení rozpisu se plánuje vývoj funkce automatického rozdělování směn.

Administrátor vybere plánovací období a spustí algoritmus.

Algoritmus musí zohlednit:

1. Směny, které pracovníci označili jako dostupné.
2. Nutnost pokrýt všechny pracovní směny.
3. Individuální limity pracovních hodin.
4. Povinné doby odpočinku.
5. Již přiřazené směny, včetně práce v jiných blocích.
6. Pevná přiřazení vedoucí/vedoucího.
7. Spravedlivé rozdělení pracovní zátěže.
8. Spravedlivé rozdělení hodin ve svátek.

Algoritmus musí nejprve dodržet všechna povinná omezení a teprve poté zvolit nejvhodnější variantu
rozdělení.

Pokud existuje více přípustných variant, použije se zvolené kritérium spravedlnosti.

Po provedení automatického rozdělení bude administrátor moci navržený rozpis zkontrolovat a ručně
upravit přiřazení.

Teprve poté se rozpis uloží nebo zveřejní.

Pokud nelze rozdělit všechny směny, systém musí zobrazit neobsazené intervaly.

Automatický algoritmus nesmí porušit povinná omezení kvůli zaplnění rozpisu.

Automatické rozdělení jedné standardní směny mezi více pracovníků se v této fázi neplánuje.

## 7. Ruční úpravy a záskoky

Ruční rozdělování směn je hlavní funkcí první verze aplikace.

Administrátor přiřazuje pracovníky na pracovní intervaly.

Při každém přiřazení systém automaticky kontroluje povinná omezení.

Pokud přiřazení není možné, operace se zamítne a zobrazí se zpráva s důvodem.

Například:

- Mezi směnami není dostatek času na odpočinek.
- Souvislé pracovní období by přesáhlo 12 hodin.
- Je překročen individuální měsíční limit hodin.
- Pracovník je v tuto dobu již přiřazen na jinou směnu.
- Je porušen povinný nepřetržitý odpočinek v týdnu.

**Částečné a úplné záskoky (Záskok)**

Systém musí podporovat zastoupení pracovníků na celé směně nebo na její části.

Například pracovník je původně přiřazen na směnu 14:00–22:00, ale může pracovat pouze do 19:00.

Administrátor bude moci přiřazení upravit takto:

- Pracovník A: 14:00–19:00.
- Pracovník B: 19:00–22:00.

Druhým pracovníkem může být člen téhož týmu i pracovník jiného bloku.

Systém musí kontrolovat, že časové intervaly pokrývají celou požadovanou směnu a nevytvářejí
zakázané překryvy.

Pokud část směny zůstane volná, musí se zobrazit jako neobsazená.

Po změně přiřazení se automaticky aktualizují pracovní hodiny a mzdy pracovníků.

Všechny změny se ukládají do historie.

**Koncept, zveřejnění a odevzdání rozpisu**

Rozpis bloku se vede po kalendářních měsících. Pro každý měsíc existuje pracovní verze (koncept),
kterou vidí a upravuje jen administrátor bloku, a zveřejněná verze, kterou vidí pracovníci.

| Akce | Význam |
|---|---|
| Uložit | Uloží rozpracované změny do pracovní verze. Pracovníci je nevidí. |
| Publikovat | Zveřejní pracovní verzi pracovníkům. U rozpisu se zobrazuje datum a čas posledního zveřejnění. |
| Odevzdat | Po potvrzení uzamkne rozpis měsíce jako podklad pro zpracování mezd. |

Pravidla:

- Dokud rozpis měsíce nebyl zveřejněn, pracovníci v něm žádné směny nevidí.
- Neuložené změny a uložené, ale dosud nezveřejněné změny jsou u rozpisu označeny upozorněním.
- Zveřejněný rozpis lze dále upravovat, například při záskoku. Změna se uloží do pracovní verze
  a pracovníkům se zobrazí až po dalším zveřejnění.
- Odevzdat nelze, dokud existují nezveřejněné změny.
- Odevzdaný rozpis je uzamčen: přiřazení, intervaly, limity ani sazby daného měsíce už nelze měnit.
- Kontrola omezení (kap. 8) zohledňuje všechna uložená přiřazení pracovníka ve všech blocích,
  zveřejněná i nezveřejněná. Dva administrátoři tak nemohou v konceptech vytvořit přiřazení, která
  by se navzájem vylučovala.
- Administrátor vidí při úpravách hodiny a zbývající limity podle pracovní verze. Pracovník vidí
  své směny, hodiny a výpočet mzdy podle zveřejněné verze, a to i u směn v jiných blocích.
- Směna přes hranici měsíců patří do rozpisu měsíce, ve kterém začíná.

## 8. Omezení pracovní doby a odpočinku

Při sestavování rozpisu je nutné dodržovat stanovená omezení pracovní doby a odpočinku.

**Souvislé pracovní období**

Přiřazení jednoho pracovníka, která na sebe bezprostředně navazují (konec jednoho je začátkem
druhého), tvoří jedno souvislé pracovní období, a to i když patří k různým směnám, dodatečným
intervalům nebo blokům. Například přiřazení 10:00–14:00 a 14:00–18:00 tvoří jedno období
10:00–18:00 a jsou přípustná.

**Hlavní pravidla**

- Souvislé pracovní období nesmí být delší než 12 hodin. Dvě sousední osmihodinové směny proto
  nelze přiřadit jednomu pracovníkovi (16 hodin); přiřazení 06:00–14:00 a 14:00–18:00 přípustné je
  (12 hodin).
- Mezi dvěma souvislými pracovními obdobími jednoho pracovníka musí být nepřetržitý denní odpočinek
  nejméně 11 hodin. Přiřazení, která na sebe nenavazují, tedy musí dělit alespoň 11 hodin.
- Obě předchozí pravidla společně zajišťují denní odpočinek 11 hodin během každých 24 po sobě
  jdoucích hodin.
- V každém kalendářním týdnu (pondělí 00:00 až neděle 24:00) musí mít pracovník nepřetržitý
  odpočinek nejméně 35 hodin (24 hodin a navazující denní odpočinek 11 hodin), který celý leží
  v tomto týdnu. Posuzuje se každý týden, do kterého přiřazení zasahuje, bez ohledu na hranice
  měsíců.
- Je zakázáno přiřadit pracovníka na překrývající se pracovní intervaly.
- Nelze překročit individuální měsíční limit pracovních hodin; bez nastaveného limitu nelze
  pracovníka v daném měsíci přiřadit. Limit se nevztahuje na vedoucí recepce (kap. 2.3).
- Kontroly musí zohledňovat směny vykonávané v jiných blocích.
- Kontroly musí zohledňovat směny na hranicích týdnů a měsíců.

Délka pracovního období a doba odpočinku se počítají podle skutečně uplynulého času, tedy včetně
přechodu na letní a zimní čas.

Aplikace nebude používat výjimky umožňující zkrácení povinného denního odpočinku.

Všechna omezení se kontrolují při vytváření a změně přiřazení a znovu při zveřejnění rozpisu.

Administrátor nebude moci povinná omezení ručně obejít.

Pravidla vycházejí z úpravy pracovní doby a doby odpočinku v zákoníku práce (délka směny, denní
odpočinek, odpočinek v týdnu), která se vztahuje i na dohody o pracích konaných mimo pracovní
poměr. Konkrétní pravidla musí být ověřena z hlediska souladu s platnou legislativou ČR
a podmínkami práce na recepci.

## 9. Spravedlivé rozdělení směn — budoucí rozšíření

V budoucnu má automatické rozdělování zohledňovat spravedlivé rozdělení práce mezi pracovníky.

Pracovníci přitom mohou mít různé měsíční limity hodin.

Systém nemá usilovat o nutné naplnění limitu každého pracovníka, protože limit určuje maximální,
nikoli potřebnou zátěž.

Samostatným kritériem bude rozdělení pracovních hodin ve státní svátky.

Algoritmus musí zohledňovat počet hodin přiřazených každému pracovníkovi na státní svátky během
kalendářního roku.

Při sestavování nového rozpisu má systém usilovat o snižování rozdílů mezi pracovníky, nakolik to
dostupnost a povinná omezení dovolí.

Započítávají se právě hodiny připadající na státní svátek, nikoli počet směn.

Po záskoku nebo změně rozpisu se statistika hodin ve svátek musí aktualizovat.

Konkrétní matematický model spravedlnosti bude určen ve fázi vývoje algoritmu.

## 10. Výpočet mzdy

Aplikace má automaticky počítat odměny pracovníků na základě přiřazených pracovních směn.

Samostatný systém evidence skutečně odpracované doby se nepředpokládá. Podkladem pro výpočet je
rozpis, který v případě potřeby upraví administrátor.

Výpočet bude zohledňovat:

- Individuální hodinovou sazbu.
- Počet placených hodin.
- Noční práci.
- Práci v sobotu a neděli.
- Práci ve státní svátky.
- Částečné záskoky.
- Platná pravidla odměňování a přestávek.

Předběžná výše příplatků:

| Druh příplatku | Výše |
|---|---|
| Noční práce (22:00–06:00) | 20 % |
| Práce v sobotu a neděli | 25 % |
| Práce ve státní svátek | 100 % |

Konečná výše příplatků, základy pro jejich výpočet a pravidla jejich souběhu musí odpovídat
skutečnému způsobu odměňování na recepci.

Pro správný výpočet bude aplikace v případě potřeby ukládat odděleně základní hodinovou sazbu
a průměrný hodinový výdělek.

**Specifika výpočtu**

- Mzda se počítá na základě přiřazených pracovních intervalů.
- Příplatky se určují s ohledem na kalendářní dny a dobu, do které pracovní směna připadá.
- Pokud pracovní interval spadá pod více druhů příplatků, počítají se podle stanovených pravidel.
- Pokud noční směna začíná poslední den měsíce a končí v následujícím měsíci, příplatek za noční
  práci patří do zúčtovacího měsíce začátku směny.
- Příplatky za práci o víkendu a/nebo ve státní svátek, které náleží za takovou noční směnu na
  hranici měsíců, patří podle pravidel pracoviště do následujícího zúčtovacího měsíce.
- Základní mzda za noční směnu na hranici měsíců patří celá do zúčtovacího měsíce začátku směny.
- Pro statistiku hodin ve svátek se započítává kalendářní datum, na které pracovní doba připadá,
  bez ohledu na měsíc zúčtování příslušného příplatku.
- Částečné záskoky se počítají podle délky pracovních intervalů přiřazených jednotlivým
  pracovníkům.
- Při změně rozpisu se mzda automaticky přepočítá.

Například pokud noční směna začíná 31. října ve 22:00 a končí 1. listopadu v 06:00, základní mzda
za celou placenou dobu (7,5 h) i příplatek za noční práci patří do října. Pokud za směnu náleží
také příplatky za víkend nebo státní svátek, patří do listopadu.

Různé druhy příplatků za jednu noční směnu tak mohou patřit do různých zúčtovacích měsíců.

**Přestávky**

Na recepci platí pevné třicetiminutové přestávky:

| Směna | Přestávka |
|---|---|
| Ranní | 11:30–12:00 |
| Odpolední | 18:00–18:30 |
| Noční | 02:00–02:30 |

Doba přestávky se neplatí. Protože přiřazení začínají a končí na celou nebo půl hodinu (kap. 4),
přiřazení buď obsahuje celou přestávku, nebo do ní vůbec nezasahuje; směnu lze tedy převzít nebo
předat například v 18:00 nebo v 18:30. Přiřazení, které přestávku obsahuje, má placenou dobu vždy
o 30 minut kratší. Přestávky jsou vázány na
denní dobu, nikoli na typ směny, a pravidlo platí pro všechna přiřazení včetně záskoků
a dodatečných intervalů.

Například:

| Přiřazení | Odpracovaná doba | Placená doba |
|---|---|---|
| 06:00–14:00 | 8 h | 7,5 h |
| 14:00–18:00 | 4 h | 4 h |
| 18:30–22:00 | 3,5 h | 3,5 h |
| 17:00–22:00 | 5 h | 4,5 h |
| 22:00–06:00 | 8 h | 7,5 h |

Neplacená doba přestávky se nezapočítává do základu příplatků. Při určování hodin připadajících
na víkend nebo státní svátek se přestávka přiřazuje ke dni, na který skutečně připadá.

Placená doba se počítá podle místního času (rozdíl časů začátku a konce na hodinách), nikoli podle
skutečně uplynulé doby. Noční směna 22:00–06:00 se tak platí 7,5 h i v noci přechodu na letní čas
(skutečně odpracováno 6,5 h) nebo na zimní čas (skutečně odpracováno 8,5 h).

Časy přestávek jsou parametry odměňování s obdobím platnosti.

**Zobrazení výsledků**

U každého pracovníka aplikace zobrazuje:

- Počet přiřazených hodin.
- Počet placených hodin.
- Základní mzdu.
- Příplatek za noční práci.
- Příplatek za víkendy.
- Příplatek za svátky.
- Celkovou částku přiznané odměny.

Pracovník bude moci vybrat měsíc a zobrazit historii výpočtů.

Při sestavování měsíčního výpočtu musí systém zohlednit, že jednotlivé příplatky za směny na
hranici měsíců mohou patřit do různých zúčtovacích období.

Administrátor bude moci prohlížet výpočty pracovníků svého bloku.

Předpokládá se export dat pro další přípravu mzdových podkladů.

Výpočet čisté mzdy, daní a odvodů na sociální a zdravotní pojištění není součástí hlavního rozsahu
projektu.

## 11. Bezpečnost a autorizace

Aplikace musí zajistit bezpečnou práci s osobními údaji pracovníků.

V první fázi se předpokládá vlastní systém registrace a přihlášení.

V budoucnu bude implementována integrace s infrastrukturou TUL v souladu s požadavky diplomové
práce a technickými možnostmi univerzity.

Hlavní bezpečnostní požadavky:

- Bezpečné ukládání hesel.
- Zabezpečené spojení HTTPS.
- Kontrola přístupových práv na serveru.
- Oddělení oprávnění pracovníka a administrátora.
- Omezení přístupu k rozpisům jiných bloků.
- Ochrana individuálních mzdových údajů.
- Bezpečná správa uživatelských relací.
- Kontrola všech omezení při změně směn.
- Zákaz neoprávněného získání administrátorských práv.
- Ukládání historie důležitých změn.
- Zálohování dat.

Běžný pracovník nesmí mít možnost měnit rozpis, limity ani mzdové údaje.

Administrátor může měnit pouze údaje svého bloku a přiřazovat pracovníky z jiných bloků na směny
svého bloku.

## 12. Požadavky na uživatelské rozhraní

Rozhraní aplikace musí být srozumitelné a pohodlné pro pracovníky recepce.

Hlavní požadavky:

- Podpora počítačů a mobilních zařízení.
- Přehledné zobrazení kalendáře.
- Týdenní tabulka pracovního rozpisu.
- Rychlé přepínání mezi měsíci a týdny.
- Vizuální zvýraznění státních svátků.
- Zobrazení volných a obsazených směn.
- Srozumitelné zobrazení porušených omezení.
- Pohodlné ruční přiřazování a změny směn.
- Zobrazení individuálních limitů hodin.
- Zobrazení výpočtů mezd.
- Oddělení dostupných funkcí podle oprávnění uživatele.

Hlavním jazykem rozhraní bude čeština.

## 13. Testování aplikace

Po implementaci má být aplikace poskytnuta pracovníkům recepce k praktickému testování.

**Funkční testování**

Ověření registrace, přihlášení, správy pracovníků, ručního přiřazování směn, záskoků, ukládání
rozpisu a výpočtu mzdy.

**Testování omezení**

Ověření správného odhalení nepřípustných přiřazení, porušení odpočinku, překročení limitů
a překryvů směn.

Zvláštní pozornost je třeba věnovat směnám přecházejícím přes půlnoc a také přiřazením na
hranicích měsíců a v různých blocích.

**Testování výpočtu mzdy**

Porovnání výsledků aplikace s předem ověřenými výpočty pro různé typy směn, víkendy, svátky
a záskoky.

Samostatně se musí ověřit noční směny na hranici měsíců a správné rozdělení příplatků do
zúčtovacích období.

**Testování bezpečnosti**

Ověření autentizace, oddělení oprávnění, ochrany osobních údajů a nemožnosti změnit rozpis bez
příslušných oprávnění.

**Uživatelské testování**

Praktické používání aplikace pracovníky recepce a sběr zpětné vazby o pohodlí, možných chybách
a chybějících funkcích.

Při implementaci automatického rozdělování se navíc provádějí testy algoritmu.

Na základě výsledků testování budou provedeny potřebné opravy a připraveno hodnocení systému.

## 14. Očekávaný výsledek a další vývoj

Výsledkem první fáze bude webová aplikace, která umožní ručně sestavovat pracovní rozpis,
spravovat pracovníky, kontrolovat omezení pracovní doby a počítat odměny.

Základní systém musí být plně funkční a vhodný pro praktické používání pracovníky recepce.

Vývoj dalších funkcí bude probíhat po dokončení hlavních funkcí.

K předpokládaným rozšířením patří:

- Zadávání dostupných směn pracovníky.
- Automatické rozdělování směn.
- Spravedlivé rozdělení hodin ve svátek.
- Další statistiky pracovní zátěže.
- Vylepšení rozhraní na základě výsledků testování.

Integrace s autentizačním systémem TUL se považuje za samostatnou fázi implementace, nutnou ke
splnění příslušného požadavku zadání diplomové práce.

V závěru diplomové práce budou zhodnoceny skutečně implementované funkce aplikace, výsledky
uživatelského testování a perspektivy dalšího vývoje.
