import React, {useState} from 'react'
import api from '../lib/api.js'
export default function Voter(){
  const [electionId,setElectionId] = useState('')
  const [candidateId,setCandidateId] = useState('CANDIDATE-1')
  const [receipt,setReceipt] = useState(null)
  const [err,setErr] = useState('')
  async function cast(e){
    e.preventDefault(); setErr('')
    try{
      const {data} = await api.post('/ballot/cast',{ electionId, candidateId },{
        headers:{ 'X-Geo-Country':'AU','X-Geo-Region':'VIC' }
      })
      setReceipt(data)
    }catch(ex){ setErr(ex?.response?.data?.message || 'Failed to cast vote') }
  }
  return (<div className="bg-white rounded shadow p-4 max-w-lg">
    <h2 className="font-semibold text-lg mb-3">Cast Vote</h2>
    <form className="space-y-3" onSubmit={cast}>
      <input className="w-full border rounded p-2" placeholder="Election UUID" value={electionId} onChange={e=>setElectionId(e.target.value)}/>
      <input className="w-full border rounded p-2" placeholder="Candidate ID" value={candidateId} onChange={e=>setCandidateId(e.target.value)}/>
      <button className="bg-blue-600 text-white rounded px-4 py-2">Submit</button>
    </form>
    {err && <div className="text-red-600 text-sm mt-2">{err}</div>}
    {receipt && <div className="mt-4 p-3 bg-gray-50 rounded border">
      <div className="font-semibold">Receipt</div>
      <div className="text-sm">ID: {receipt.receiptId}</div>
      <div className="text-sm">Timestamp UTC: {receipt.timestampUtc}</div>
      {receipt.geoCountry && <div className="text-sm">Country: {receipt.geoCountry}</div>}
      {receipt.geoRegion && <div className="text-sm">Region: {receipt.geoRegion}</div>}
    </div>}
  </div>)
}
