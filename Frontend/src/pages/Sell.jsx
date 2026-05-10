import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { api } from '../api/api.js'
import { formStyle, inputStyle, labelStyle, buttonStyle, pageWrapper, heroTitle } from '../components/styles'

const CHASSIS_OPTIONS = ['sedan','suv','hatchback','coupe','convertible','wagon','pickup','van']

export default function Sell() {
    const [form, setForm] = useState({ brand: '', model: '', chassis: '', year: '', price: '' })
    const [image, setImage] = useState(null)
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState('')
    const navigate = useNavigate()

    const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value })

    const handlePublish = async () => {
        if (!form.brand || !form.model || !form.chassis || !form.year || !form.price) {
            setError('Please fill all fields!')
            return
        }
        setLoading(true)
        setError('')
        try {
            await api.publishAd({
                brand: form.brand,
                model: form.model,
                chassis: form.chassis,
                year: parseInt(form.year),
                price: parseInt(form.price)
            }, image)
            alert('Car published successfully!')
            navigate('/search')
        } catch {
            setError('Failed to publish. Make sure you are logged in.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <div style={pageWrapper}>
            <h2 style={heroTitle}>
                List it. <span style={{ color: 'orange' }}>Sell it.</span> Drive away.
            </h2>
            <div style={formStyle}>
                <label style={labelStyle}>Brand</label>
                <input style={inputStyle} type="text" name="brand" placeholder="Brand" onChange={handleChange} />
                <label style={labelStyle}>Model</label>
                <input style={inputStyle} type="text" name="model" placeholder="Model" onChange={handleChange} />
                <label style={labelStyle}>Chassis Type</label>
                <select style={inputStyle} name="chassis" onChange={handleChange}>
                    <option value="">Select chassis type</option>
                    {CHASSIS_OPTIONS.map(c => <option key={c} value={c}>{c.charAt(0).toUpperCase() + c.slice(1)}</option>)}
                </select>
                <label style={labelStyle}>Year</label>
                <input style={inputStyle} type="number" name="year" placeholder="Year" onChange={handleChange} />
                <label style={labelStyle}>Price</label>
                <input style={inputStyle} type="number" name="price" placeholder="Price" onChange={handleChange} />
                <label style={labelStyle}>Image</label>
                <input style={inputStyle} type="file" accept="image/*" onChange={e => setImage(e.target.files[0])} />
                {error && <p style={{ color: 'red', marginBottom: '10px' }}>{error}</p>}
                <button style={buttonStyle} onClick={handlePublish} disabled={loading}>
                    {loading ? 'Publishing...' : 'Publish'}
                </button>
            </div>
        </div>
    )
}