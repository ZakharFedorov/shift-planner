import { useQuery } from '@tanstack/react-query'
import { apiGet } from '../../api/http'

interface Health {
  status: string
}

export function useSystemHealth() {
  return useQuery({
    queryKey: ['system', 'health'],
    queryFn: ({ signal }) => apiGet<Health>('/actuator/health', signal),
    refetchInterval: 60_000,
    retry: false,
  })
}
