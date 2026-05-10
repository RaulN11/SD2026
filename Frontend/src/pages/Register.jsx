import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { api } from '../api/api.js'
import { formStyle, inputStyle, labelStyle, buttonStyle, pageWrapper, heroTitle } from '../components/styles.js'

export default function Register() {
    const [form, setForm] = useState({ firstName: '', lastName: '', email: '', password: '', role: 'client' })
    const [error, setError] = useState('')
    const navigate = useNavigate()

    const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value })

    const handleRegister = async () => {
        if (!form.firstName || !form.lastName || !form.email || !form.password) {
            setError('All fields are required.')
            return
        }
        try {
            await api.register(form)
            navigate('/login')
        } catch {
            setError('Registration failed. Email may already be in use.')
        }
    }

    return (
        <div style={pageWrapper}>
            <h2 style={heroTitle}>
                Create your <span style={{ color: 'orange' }}>CarKet</span> Account
            </h2>
            <div style={formStyle}>
                <label style={labelStyle}>First Name</label>
                <input style={inputStyle} type="text" name="firstName" placeholder="First Name" onChange={handleChange} />
                <label style={labelStyle}>Last Name</label>
                <input style={inputStyle} type="text" name="lastName" placeholder="Last Name" onChange={handleChange} />
                <label style={labelStyle}>Email</label>
                <input style={inputStyle} type="email" name="email" placeholder="Email" onChange={handleChange} />
                <label style={labelStyle}>Role</label>
                <select style={inputStyle} name="role" onChange={handleChange}>
                    <option value="client">Client</option>
                    <option value="admin">Admin</option>
                </select>
                <label style={labelStyle}>Password</label>
                <input style={inputStyle} type="password" name="password" placeholder="Password" onChange={handleChange} />
                {error && <p style={{ color: 'red', marginBottom: '10px' }}>{error}</p>}
                <button style={buttonStyle} onClick={handleRegister}>Register</button>
            </div>
            <div style={{ fontFamily: 'Oswald', fontSize: '20px', marginTop: '20px' }}>
                <p>Already have an account? <Link to="/login" style={{ color: 'orange' }}>Login here</Link></p>
            </div>
        </div>
    )
}