/** RFC 7807 problem returned by the backend (see backend GlobalExceptionHandler). */
export interface ProblemDetail {
  type?: string
  title?: string
  status?: number
  detail?: string
  code?: string
  errors?: { field: string; message: string }[]
}

/** Error thrown for every non-2xx response; `code` is the machine-readable backend error code. */
export class ApiError extends Error {
  readonly status: number
  readonly code: string
  readonly problem: ProblemDetail | undefined

  constructor(status: number, code: string, problem?: ProblemDetail) {
    super(problem?.detail ?? `HTTP ${status}`)
    this.name = 'ApiError'
    this.status = status
    this.code = code
    this.problem = problem
  }
}

/** Code used when the server could not be reached at all. */
export const NETWORK_ERROR = 'NETWORK_ERROR'

export async function apiGet<T>(path: string, signal?: AbortSignal): Promise<T> {
  let response: Response
  try {
    response = await fetch(path, {
      method: 'GET',
      credentials: 'same-origin',
      headers: { Accept: 'application/json' },
      signal,
    })
  } catch (error) {
    if (error instanceof DOMException && error.name === 'AbortError') {
      throw error
    }
    throw new ApiError(0, NETWORK_ERROR)
  }
  if (!response.ok) {
    throw await toApiError(response)
  }
  return (await response.json()) as T
}

export async function toApiError(response: Response): Promise<ApiError> {
  const contentType = response.headers.get('Content-Type') ?? ''
  let problem: ProblemDetail | undefined
  if (contentType.includes('json')) {
    try {
      problem = (await response.json()) as ProblemDetail
    } catch {
      problem = undefined
    }
  }
  return new ApiError(response.status, problem?.code ?? `HTTP_${response.status}`, problem)
}
