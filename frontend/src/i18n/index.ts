import i18n from 'i18next'
import { initReactI18next } from 'react-i18next'
import cs from './locales/cs.json'

// All UI texts live in locale files; components never contain hard-coded Czech strings.
export const resources = {
  cs: { translation: cs },
} as const

void i18n.use(initReactI18next).init({
  resources,
  lng: 'cs',
  fallbackLng: 'cs',
  initAsync: false,
  interpolation: {
    // React already escapes rendered values.
    escapeValue: false,
  },
})

export default i18n
