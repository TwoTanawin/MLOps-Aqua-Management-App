'use client'

import { Thermometer, Plus, Home, LineChart } from 'lucide-react';
import { useState } from 'react';

export default function Page() {
  const [showNotice, setShowNotice] = useState(true);

  return (
    <div className="min-h-screen bg-gray-50 pt-20 pb-28 px-4 relative">
      {/* Top Bar */}
      <div className="fixed top-0 left-0 right-0 bg-blue-800 text-white py-4 z-10 px-4">
        <div className="flex justify-between items-center text-sm">
          <span>23:40 🔇</span>
          <div className="flex items-center gap-2">
            <div className="w-4 h-4 bg-white rounded-full"></div>
          </div>
        </div>
        <div className="mt-2 flex items-center justify-between">
          <div className="flex items-center gap-2">
            <span role="img" aria-label="farm">🏢</span>
            <span className="font-semibold">Bangkok Office Farm updated 3</span>
            <Thermometer size={16} />
          </div>
          <button className="bg-white text-blue-800 text-sm px-3 py-1 rounded font-semibold flex items-center gap-1">
            <Plus size={14} /> Add Pond
          </button>
        </div>
        <div className="mt-2 text-sm flex gap-6">
          <span>Active Ponds <span className="bg-white text-blue-800 px-2 rounded-full">11</span></span>
          <span>Total Ponds <span className="bg-white text-blue-800 px-2 rounded-full">16</span></span>
        </div>
      </div>

      {/* Notice Box */}
      {showNotice && (
        <div className="bg-blue-100 border border-blue-300 text-sm p-4 rounded-md mt-4">
          <p className="mb-4">Dear Customer,<br />To provide you with the most accurate services and exciting new features, please take a moment to verify that your farm location saved is correct. Thank you for your cooperation!</p>
          <div className="flex justify-around">
            <button onClick={() => setShowNotice(false)} className="bg-blue-800 text-white px-4 py-2 rounded">Close</button>
            <button className="bg-blue-800 text-white px-4 py-2 rounded">Add Location</button>
          </div>
        </div>
      )}

      {/* Pond Card */}
      <div className="bg-white rounded-lg shadow p-4 mt-4">
        <div className="flex justify-between items-center font-semibold text-gray-700">
          <span>Bkk Office Pond 2 - The re</span>
          <span className="text-sm text-red-500">● updated 948 days ago</span>
        </div>
        <div className="grid grid-cols-3 text-center text-sm text-gray-800 mt-4">
          <div>
            <p className="font-bold">93</p>
            <p>DoC</p>
          </div>
          <div>
            <p>-</p>
            <p>Temp</p>
          </div>
          <div>
            <p>20.0</p>
            <p>Salinity</p>
          </div>
          <div>
            <p>-</p>
            <p>DO (ppm)</p>
          </div>
          <div>
            <p>-</p>
            <p>pH</p>
          </div>
          <div>
            <p>67</p>
            <p>pcs/kg</p>
          </div>
          <div>
            <p>-</p>
            <p>DO (%)</p>
          </div>
          <div>
            <p className="font-semibold">Manual</p>
            <p>Aeration</p>
          </div>
          <div>
            <p>0</p>
            <p>On</p>
          </div>
          <div>
            <p>0</p>
            <p>Off</p>
          </div>
        </div>

        {/* Action Buttons */}
        <div className="grid grid-cols-2 gap-2 mt-4">
          <button className="border border-blue-800 rounded p-2 flex justify-center items-center text-blue-800 font-semibold">
            🧪 SpinTouch
          </button>
          <button className="border border-blue-800 rounded p-2 flex justify-center items-center text-blue-800 font-semibold">
            🖼 Pond Photo
          </button>
          <button className="border border-blue-800 rounded p-2 flex justify-center items-center text-blue-800 font-semibold">
            🦐 Shrimp Welfare
          </button>
          <button className="border border-blue-800 rounded p-2 flex justify-center items-center text-blue-800 font-semibold">
            📝 Logbook
          </button>
        </div>
      </div>

      {/* Another Pond Card Example */}
      <div className="bg-white rounded-lg shadow p-4 mt-4">
        <div className="flex justify-between items-center font-semibold text-gray-700">
          <span>Bucket</span>
          <span className="text-sm text-red-500">● updated 900 days ago</span>
        </div>
        <div className="grid grid-cols-3 text-center text-sm text-gray-800 mt-4">
          <div>
            <p className="font-bold">595</p>
            <p>DoC</p>
          </div>
          <div>
            <p>-</p>
            <p>Temp</p>
          </div>
          <div>
            <p>20.0</p>
            <p>Salinity</p>
          </div>
        </div>
      </div>

      {/* Bottom Nav */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t flex justify-around items-center h-16 text-xs text-gray-700">
        <button className="flex flex-col items-center">
          <Home size={20} />
          Farm
        </button>
        <button className="flex flex-col items-center relative">
          <LineChart size={20} />
          <span className="absolute top-0 right-0 w-2 h-2 bg-red-500 rounded-full"></span>
          Price
        </button>
        <button className="flex flex-col items-center">
          •••
          More
        </button>
      </div>
    </div>
  );
}
