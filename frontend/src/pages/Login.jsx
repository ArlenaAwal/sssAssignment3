import React, {useState} from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../lib/api.js'
export default function Login({onLogin}){
  const [username,setUsername] = useState('admin')
  const [password,setPassword] = useState('admin')
  const [otp,setOtp] = useState('')
  const [error,setError] = useState('')
  const navigate = useNavigate()
  async function submit(e){
    e.preventDefault(); setError('')
    try{
      const {data} = await api.post('/auth/login',{username,password,otp})
      localStorage.setItem('token', data.accessToken)
      const me = await api.get('/auth/me'); onLogin?.(me.data); navigate('/')
    }catch(err){ setError(err?.response?.data?.message || 'Login failed') }
  }
  return (<div className="max-w-md mx-auto bg-white rounded-xl shadow p-6">
    <h2 className="text-xl font-semibold mb-4">Login</h2>
    <form className="space-y-3" onSubmit={submit}>
      <input className="w-full border p-2 rounded" placeholder="Username" value={username} onChange={e=>setUsername(e.target.value)}/>
      <input className="w-full border p-2 rounded" placeholder="Password" type="password" value={password} onChange={e=>setPassword(e.target.value)}/>
      <input className="w-full border p-2 rounded" placeholder="OTP (if MFA)" value={otp} onChange={e=>setOtp(e.target.value)}/>
      {error && <div className="text-red-600 text-sm">{error}</div>}
      <button className="w-full bg-blue-600 text-white rounded py-2">Sign in</button>
    </form>
  </div>)
}
