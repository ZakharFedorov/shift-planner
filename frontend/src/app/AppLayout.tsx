import { useTranslation } from 'react-i18next'
import { Link, NavLink, Outlet } from 'react-router'
import { SystemStatus } from '../features/system/SystemStatus'

export function AppLayout() {
  const { t } = useTranslation()

  return (
    <div className="app">
      <header className="app__header">
        <Link to="/" className="app__brand" title={t('app.fullName')}>
          {t('app.name')}
        </Link>
        <nav aria-label={t('nav.main')} className="app__nav">
          <NavLink to="/" end>
            {t('nav.home')}
          </NavLink>
        </nav>
        <SystemStatus />
      </header>
      <main className="app__main">
        <Outlet />
      </main>
    </div>
  )
}
