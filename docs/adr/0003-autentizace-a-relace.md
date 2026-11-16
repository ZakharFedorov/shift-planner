# ADR-0003: Autentizace a správa relací

- **Stav:** přijato
- **Datum:** 16. 11. 2026

## Kontext

Zadání vyžaduje autentizaci a autorizaci s napojením na infrastrukturu TUL a klade důraz na
zabezpečení dat a komunikace. Způsob napojení (SAML/Shibboleth nebo OIDC) bude určen po dohodě
s IT oddělením TUL; do té doby aplikace potřebuje vlastní přihlášení. Klientem je SPA dostupná pod
stejným originem jako API (ADR-0001).

## Zvažované varianty

Způsob přihlášení a udržení relace:

1. **Relace na serveru, identifikátor v cookie**: relaci lze okamžitě zneplatnit, identifikátor
   není dostupný skriptům; vyžaduje ochranu proti CSRF.
2. **JWT v úložišti prohlížeče (hlavička `Authorization`)**: bezstavový server, ale token je
   čitelný skripty (riziko při XSS) a nelze ho jednoduše zneplatnit.
3. **JWT v cookie `HttpOnly`**: token není čitelný skripty, jeho zneplatnění však stejně vyžaduje
   stav na serveru, takže proti variantě 1 nepřináší výhodu.
4. **Pouze přihlášení přes TUL**: přímo plní zadání, ale vývoj by závisel na externím termínu.

Hashovací funkce pro hesla:

1. **Argon2id**: paměťově náročná funkce odolná proti útokům na GPU, první doporučení OWASP.
2. **BCrypt**: osvědčená a ve Spring Security výchozí, ale s menší odolností proti specializovanému
   hardwaru a s omezením délky hesla na 72 bajtů.

## Rozhodnutí

- Vlastní přihlášení e-mailem a heslem, relace na serveru (varianta 1).
- Cookie relace má příznaky `HttpOnly`, `Secure` a `SameSite=Strict`.
- Při přihlášení se vytvoří nová relace (ochrana proti fixaci relace).
- Všechny požadavky měnící stav jsou chráněny CSRF tokenem.
- Hesla se ukládají jako hash **Argon2id**. Hash je uložen s označením algoritmu, takže parametry
  i algoritmus lze později změnit bez zneplatnění existujících hesel.
- Počet neúspěšných pokusů o přihlášení je omezen.
- Autentizace je oddělena od autorizace. Výsledkem každého způsobu přihlášení je interní uživatel
  aplikace; autorizace (role, blok, vlastnictví zdroje) pracuje pouze s ním.

## Důsledky

- Přihlášení přes TUL lze doplnit jako další způsob autentizace: externí identita se přiřadí
  k internímu uživateli a autorizace se nemění.
- Stav relací drží server. Při provozu jedné instance aplikace to není omezení (ADR-0001).
- SPA musí k požadavkům měnícím stav přikládat CSRF token.
- Implementace Argon2 ve Spring Security vyžaduje knihovnu Bouncy Castle.
- Úložiště relací, parametry omezení pokusů o přihlášení a požadavky na hesla se určí při návrhu
  modulu `auth`.
