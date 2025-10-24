import React, { useEffect, useState } from 'react'
import { Routes, Route, Link, useNavigate } from 'react-router-dom'
import Login from './Login.jsx'
import Admin from './Admin.jsx'
import Voter from './Voter.jsx'
import api from '../lib/api.js'

export default function App(){
  const [me,setMe] = useState(null)
  const navigate = useNavigate()
  useEffect(()=>{ const t = localStorage.getItem('token'); if(!t) return; api.get('/auth/me').then(r=>setMe(r.data)).catch(()=>{}) },[])
  function logout(){ localStorage.removeItem('token'); setMe(null); navigate('/login') }
  return (<div className="min-h-screen">
    <nav className="bg-white border-b shadow-sm">
      <div className="max-w-5xl mx-auto flex justify-between p-4">
        <div className="space-x-4">
          <Link to="/" className="font-semibold">eVote</Link>
          <Link to="/voter" className="text-blue-600">Voter</Link>
          <Link to="/admin" className="text-blue-600">Admin</Link>
        </div>
        <div>
          {me ? (<div className="flex items-center gap-3">
            <span className="text-sm text-gray-600">Hello, {me.username}</span>
            <button onClick={logout} className="text-sm px-3 py-1 bg-gray-800 text-white rounded">Logout</button>
          </div>) : (<Link to="/login" className="text-sm px-3 py-1 bg-blue-600 text-white rounded">Login</Link>)}
        </div>
      </div>
    </nav>
    <main className="max-w-5xl mx-auto p-6">
      <Routes>
        <Route path="/" element={<Landing/>}/>
        <Route path="/login" element={<Login onLogin={(u)=>setMe(u)}/>}/>
        <Route path="/admin" element={<Admin/>}/>
        <Route path="/voter" element={<Voter/>}/>
      </Routes>
    </main>
  </div>)
}
function Landing(){ return (<div className="prose"><h1>Welcome to eVote</h1><p>Use the Admin page to create an election; then cast a vote in Voter page.</p></div>) }
