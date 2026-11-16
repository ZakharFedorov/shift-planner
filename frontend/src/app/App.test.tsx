import { screen } from '@testing-library/react'
import { describe, expect, it, vi } from 'vitest'
import { renderRoute } from '../test/renderRoute'

function mockHealth(status: number, body: unknown) {
  vi.stubGlobal(
    'fetch',
    vi.fn().mockResolvedValue(
      new Response(JSON.stringify(body), { status, headers: { 'Content-Type': 'application/json' } }),
    ),
  )
}

describe('application shell', () => {
  it('renders the Czech home page and reports a healthy backend', async () => {
    mockHealth(200, { status: 'UP' })

    renderRoute('/')

    expect(screen.getByRole('heading', { name: 'Vítejte' })).toBeInTheDocument()
    expect(screen.getByRole('link', { name: 'Směny recepce' })).toBeInTheDocument()
    expect(await screen.findByText('Systém je v pořádku')).toBeInTheDocument()
  })

  it('reports an unavailable backend', async () => {
    mockHealth(503, { status: 'DOWN' })

    renderRoute('/')

    expect(await screen.findByText('Server je nedostupný')).toBeInTheDocument()
  })

  it('shows the not-found page for an unknown URL', () => {
    mockHealth(200, { status: 'UP' })

    renderRoute('/neexistuje')

    expect(screen.getByRole('heading', { name: 'Stránka nenalezena' })).toBeInTheDocument()
    expect(screen.getByRole('link', { name: 'Zpět na přehled' })).toHaveAttribute('href', '/')
  })
})
