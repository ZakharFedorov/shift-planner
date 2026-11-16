import { QueryClient } from '@tanstack/react-query'
import { ApiError } from '../api/http'

const MAX_RETRIES = 2

/** Client errors (4xx) are final: retrying a 401/403/409 never helps. */
function shouldRetry(failureCount: number, error: unknown): boolean {
  if (error instanceof ApiError && error.status >= 400 && error.status < 500) {
    return false
  }
  return failureCount < MAX_RETRIES
}

export function createQueryClient(): QueryClient {
  return new QueryClient({
    defaultOptions: {
      queries: {
        staleTime: 30_000,
        refetchOnWindowFocus: false,
        retry: shouldRetry,
      },
    },
  })
}
