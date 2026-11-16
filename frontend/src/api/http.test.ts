import { describe, expect, it, vi } from 'vitest'
import { ApiError, apiGet, NETWORK_ERROR } from './http'

function jsonResponse(body: unknown, status: number, contentType = 'application/json'): Response {
  return new Response(JSON.stringify(body), { status, headers: { 'Content-Type': contentType } })
}

describe('apiGet', () => {
  it('returns parsed JSON for a successful response', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(jsonResponse({ status: 'UP' }, 200)))

    await expect(apiGet<{ status: string }>('/actuator/health')).resolves.toEqual({ status: 'UP' })
  })

  it('maps a problem detail to ApiError with the backend code', async () => {
    const problem = { title: 'Nepřihlášený uživatel', status: 401, code: 'UNAUTHORIZED' }
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(jsonResponse(problem, 401, 'application/problem+json')))

    const error = await apiGet('/api/anything').catch((e: unknown) => e)

    expect(error).toBeInstanceOf(ApiError)
    expect(error).toMatchObject({ status: 401, code: 'UNAUTHORIZED' })
  })

  it('falls back to an HTTP status code when the body is not a problem detail', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response('Bad gateway', { status: 502 })))

    await expect(apiGet('/api/anything')).rejects.toMatchObject({ status: 502, code: 'HTTP_502' })
  })

  it('reports a network failure as NETWORK_ERROR', async () => {
    vi.stubGlobal('fetch', vi.fn().mockRejectedValue(new TypeError('Failed to fetch')))

    await expect(apiGet('/api/anything')).rejects.toMatchObject({ status: 0, code: NETWORK_ERROR })
  })
})
