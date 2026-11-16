import react from '@vitejs/plugin-react'
import { defineConfig } from 'vitest/config'

const backendUrl = 'http://localhost:8080'

// In development the SPA and the API share one origin through this proxy, exactly as they will
// behind the reverse proxy in production (ADR-0001). This keeps cookies same-site and avoids CORS.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    strictPort: true,
    proxy: {
      '/api': backendUrl,
      '/actuator': backendUrl,
    },
  },
  test: {
    environment: 'jsdom',
    setupFiles: ['./src/test/setup.ts'],
    css: false,
    restoreMocks: true,
    unstubGlobals: true,
  },
})
