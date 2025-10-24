import React, {useEffect, useState} from 'react'
import api from '../lib/api.js'
export default function Admin(){
  const [list,setList] = useState([])
  const [code,setCode] = useState('EL-2025')
  const [name,setName] = useState('General Election 2025')
  const [start,setStart] = useState(new Date(Date.now()-60000).toISOString())
  const [end,setEnd] = useState(new Date(Date.now()+86400000).toISOString())
  const [msg,setMsg] = useState('')
  async function load(){ try{ const {data}=await api.get('/admin/elections'); setList(data) }catch{} }
  useEffect(()=>{ load() }, [])
  async function create(e){
    e.preventDefault(); setMsg('')
    try{
      const {data} = await api.post('/admin/elections',{code,name,startTimeUtc:start,endTimeUtc:end})
      setMsg('Created '+data.id); await load()
    }catch(err){ setMsg('Failed: '+(err?.response?.data?.message || 'error')) }
  }
  return (<div className="space-y-6">
    <div className="bg-white p-4 rounded shadow">
      <h2 className="font-semibold text-lg mb-3">Create Election</h2>
      <form className="grid grid-cols-2 gap-3" onSubmit={create}>
        <input className="border p-2 rounded" placeholder="Code" value={code} onChange={e=>setCode(e.target.value)}/>
        <input className="border p-2 rounded" placeholder="Name" value={name} onChange={e=>setName(e.target.value)}/>
        <input className="border p-2 rounded col-span-2" placeholder="Start ISO" value={start} onChange={e=>setStart(e.target.value)}/>
        <input className="border p-2 rounded col-span-2" placeholder="End ISO" value={end} onChange={e=>setEnd(e.target.value)}/>
        <button className="bg-green-600 text-white rounded px-4 py-2 col-span-2">Create</button>
      </form>
      {msg && <div className="text-sm mt-2">{msg}</div>}
    </div>
    <div className="bg-white p-4 rounded shadow">
      <h2 className="font-semibold text-lg mb-3">Elections</h2>
      <table className="w-full text-sm">
        <thead><tr className="text-left border-b"><th>Code</th><th>Name</th><th>Active</th><th>Start</th><th>End</th></tr></thead>
        <tbody>{list.map(x=>(<tr key={x.id} className="border-b">
          <td>{x.code}</td><td>{x.name}</td><td>{x.active? 'Yes':'No'}</td>
          <td>{x.startTimeUtc}</td><td>{x.endTimeUtc}</td>
        </tr>))}</tbody>
      </table>
    </div>
  </div>)
}
