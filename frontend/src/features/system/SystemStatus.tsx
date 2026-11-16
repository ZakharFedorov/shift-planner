import { useTranslation } from 'react-i18next'
import { useSystemHealth } from './useSystemHealth'

type State = 'checking' | 'up' | 'down'

export function SystemStatus() {
  const { t } = useTranslation()
  const health = useSystemHealth()

  let state: State
  if (health.isPending) {
    state = 'checking'
  } else if (health.data?.status === 'UP') {
    state = 'up'
  } else {
    state = 'down'
  }

  const labels: Record<State, string> = {
    checking: t('system.checking'),
    up: t('system.up'),
    down: t('system.down'),
  }

  return (
    <p className={`system-status system-status--${state}`} role="status">
      <span className="system-status__dot" aria-hidden="true" />
      {labels[state]}
    </p>
  )
}
