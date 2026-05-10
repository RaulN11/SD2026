import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { formStyle, inputStyle, labelStyle, buttonStyle, pageWrapper, heroTitle } from '../components/styles'

const CHASSIS_OPTIONS = ['sedan','suv','hatchback','coupe','convertible','wagon','pickup','van']

export default function Search() {
    const [brand, setBrand] = useState('')
    const [model, setModel] = useState('')
    const [chassis, setChassis] = useState('')
    const navigate = useNavigate()

    const handleSearch = () => {
        const params = new URLSearchParams()
        if (brand) params.append('brand', brand)
        if (model) params.append('model', model)
        if (chassis) params.append('chassis', chassis)
        navigate(`/results?${params}`)
    }

    return (
        <div style={pageWrapper}>
            <h2 style={heroTitle}>
                Search for your <span style={{ color: 'orange' }}>next car.</span>
            </h2>
            <div style={formStyle}>
                <label style={labelStyle}>Brand</label>
                <input style={inputStyle} type="text" placeholder="Brand" value={brand} onChange={e => setBrand(e.target.value)} />
                <label style={labelStyle}>Model</label>
                <input style={inputStyle} type="text" placeholder="Model" value={model} onChange={e => setModel(e.target.value)} />
                <label style={labelStyle}>Chassis</label>
                <select style={inputStyle} value={chassis} onChange={e => setChassis(e.target.value)}>
                    <option value="">Select chassis type</option>
                    {CHASSIS_OPTIONS.map(c => <option key={c} value={c}>{c.charAt(0).toUpperCase() + c.slice(1)}</option>)}
                </select>
                <button style={buttonStyle} onClick={handleSearch}>Search</button>
            </div>
        </div>
    )
}