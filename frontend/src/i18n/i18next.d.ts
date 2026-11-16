import 'i18next'
import type cs from './locales/cs.json'

// Makes t('...') keys type-checked against the Czech locale file.
declare module 'i18next' {
  interface CustomTypeOptions {
    defaultNS: 'translation'
    resources: {
      translation: typeof cs
    }
  }
}
