// src/app/auth/page.tsx
'use client'

import { useState, useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { useSearchParams } from 'next/navigation'

export default function Page() {
  interface UserInfo {
    name?: string;
    email?: string;
    login?: string;
    avatar_url?: string;
    token?: string;
    tokenType?: string;
    expiresAt?: string;
    userId?: string;
  }

  const [userInfo, setUserInfo] = useState<UserInfo | null>(null)
  const [loading, setLoading] = useState<boolean>(true)
  const [error, setError] = useState<string | null>(null)
  const router = useRouter()
  const searchParams = useSearchParams()

  useEffect(() => {
    // Check if we have a code parameter from OAuth redirect
    const code = searchParams.get('code')
    if (code) {
      console.log('OAuth code:', code) // Use the code for debugging or further logic
    }
    
    // Fetch user info from the auth endpoint
    const fetchUserInfo = async () => {
      try {
        const response = await fetch('http://localhost:8082/auth', {
          method: 'GET',
          credentials: 'include',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          }
        })

        if (!response.ok) {
          // If not authenticated, redirect to login page
          if (response.status === 401 || response.status === 403) {
            window.location.href = 'http://localhost:8082/login'
            throw new Error('Authentication required')
          }
          throw new Error(`Server responded with status: ${response.status}`)
        }
        
        const data = await response.json()
        setUserInfo(data)
        setLoading(false)
      } catch (err: unknown) {
        if (err instanceof Error && err.message !== 'Authentication required') {
          setError(err.message)
          setLoading(false)
        }
      }
    }
    
    // Execute the fetch
    fetchUserInfo()
  }, [searchParams])

  const handleLogout = async () => {
    try {
      const response = await fetch('http://localhost:8082/logout', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        }
      })
      
      if (response.ok) {
        // Clear any local storage or state if needed
        router.push('/')
      } else {
        throw new Error(`Logout failed with status: ${response.status}`)
      }
    } catch (err: unknown) {
      if (err instanceof Error) {
        setError('Logout failed: ' + err.message)
      } else {
        setError('An unknown error occurred during logout.')
      }
    }
  }

  const handleLogin = () => {
    window.location.href = 'http://localhost:8082/oauth2/authorization/github'
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <div className="bg-white p-8 rounded-lg shadow-md max-w-md w-full text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500 mx-auto"></div>
          <p className="mt-4 text-gray-600">Authenticating...</p>
        </div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <div className="bg-white p-8 rounded-lg shadow-md max-w-md w-full">
          <h1 className="text-2xl font-bold text-red-600 mb-4">Authentication Error</h1>
          <p className="text-gray-700 mb-4">{error}</p>
          <div className="flex flex-col space-y-3">
            <button 
              onClick={handleLogin}
              className="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-md transition duration-200"
            >
              Try Again with GitHub
            </button>
            <button 
              onClick={() => router.push('/')}
              className="w-full bg-gray-500 hover:bg-gray-600 text-white font-medium py-2 px-4 rounded-md transition duration-200"
            >
              Return to Home
            </button>
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center p-4">
      <div className="bg-white p-8 rounded-lg shadow-md max-w-md w-full">
        <h1 className="text-2xl font-bold text-blue-600 mb-6">Authenticated Area</h1>
        
        {userInfo ? (
          <div className="bg-gray-50 p-4 rounded-md mb-6">
            <h2 className="text-lg font-semibold text-gray-800 mb-2">User Information</h2>
            <div className="bg-gray-100 p-3 rounded text-sm overflow-x-auto">
              {typeof userInfo === 'string' ? (
                <pre className="whitespace-pre-wrap">{userInfo}</pre>
              ) : (
                <div>
                  <p><strong>Name:</strong> {userInfo.name || 'N/A'}</p>
                  <p><strong>Email:</strong> {userInfo.email || 'N/A'}</p>
                  <p><strong>User ID:</strong> {userInfo.userId || 'N/A'}</p>
                  {userInfo.login && <p><strong>GitHub:</strong> {userInfo.login}</p>}
                  
                  {/* Token Information Section */}
                  {userInfo.token && (
                    <div className="mt-3 pt-3 border-t border-gray-200">
                      <p className="font-semibold">OAuth Token Information</p>
                      <p className="mt-1"><strong>Token Type:</strong> {userInfo.tokenType || 'N/A'}</p>
                      <p><strong>Expires:</strong> {userInfo.expiresAt || 'N/A'}</p>
                      <div className="mt-1">
                        <p><strong>Access Token:</strong></p>
                        <div className="bg-gray-200 p-2 rounded mt-1 overflow-x-auto max-h-24 text-xs">
                          <code className="break-all">{userInfo.token}</code>
                        </div>
                      </div>
                    </div>
                  )}
                  
                  {userInfo.avatar_url && (
                    <div className="mt-3">
                      <img 
                        src={userInfo.avatar_url} 
                        alt="Profile" 
                        className="w-16 h-16 rounded-full"
                      />
                    </div>
                  )}
                </div>
              )}
            </div>
          </div>
        ) : (
          <div className="text-center mb-6">
            <p className="text-gray-700">No user information available.</p>
          </div>
        )}
        
        <div className="flex flex-col space-y-3">
          <button 
            onClick={handleLogout}
            className="bg-red-500 hover:bg-red-600 text-white font-medium py-2 px-4 rounded-md transition duration-200"
          >
            Logout
          </button>
          
          <button 
            onClick={() => router.push('/')}
            className="bg-gray-500 hover:bg-gray-600 text-white font-medium py-2 px-4 rounded-md transition duration-200"
          >
            Back to Home
          </button>
        </div>
      </div>
    </div>
  )
}