// src/app/page.tsx
'use client'

import { useState, useEffect } from 'react'
import Link from 'next/link'

export default function Home() {
  const [message, setMessage] = useState<string>('')

  useEffect(() => {
    // Fetch the home endpoint from your Spring Boot server
    fetch('http://localhost:8082/', {
      method: 'GET',
      credentials: 'include'
    })
      .then(response => response.text())
      .then(data => setMessage(data))
      .catch(error => console.error('Error fetching home data:', error))
  }, [])

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col items-center justify-center p-4">
      <div className="bg-white rounded-lg shadow-md p-8 max-w-md w-full">
        <h1 className="text-3xl font-bold text-center text-blue-600 mb-6">
          HydroNeo Authentication
        </h1>

        <div className="mb-6 p-4 bg-gray-50 rounded-md text-center">
          <p className="text-gray-700">{message || 'Loading...'}</p>
        </div>

        <div className="space-y-4">
          <Link
            href="/auth"
            className="block w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-md text-center transition duration-200"
          >
            Access Secured Area
          </Link>

          <p className="text-sm text-gray-500 text-center mt-4">
            Click the button above to authenticate with OAuth2
          </p>
        </div>
      </div>
    </div>
  )
}