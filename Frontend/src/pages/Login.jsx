import {useState}  from 'react'
import {useNavigate, Link} from "react-router-dom"
import {api} from "../api/api.js"
import {formStyle, inputStyle, labelStyle, buttonStyle, pageWrapper, heroTitle}  from "../components/styles.js"

export default function Login(){
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const navigate = useNavigate()

    const handleLogin = async () => {
        try{
            await api.login(email, password)
            navigate('/search')
        }catch {
            setError("Invalid email or password")
        }
    }
    return (
        <div style={pageWrapper}>
            <h2 style={heroTitle}>
                Log in your <span style={{ color: 'orange' }}>CarKet</span> Account
            </h2>
            <div style={formStyle}>
                <label style={labelStyle}>Email Address</label>
                <input
                    style={inputStyle}
                    type="email"
                    placeholder="Email Address"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    onKeyDown={e => e.key === 'Enter' && handleLogin()}
                />
                <label style={labelStyle}>Password</label>
                <input
                    style={inputStyle}
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    onKeyDown={e => e.key === 'Enter' && handleLogin()}
                />
                {error && <p style={{ color: 'red', marginBottom: '10px' }}>{error}</p>}
                <button style={buttonStyle} onClick={handleLogin}>Login</button>
            </div>
            <div style={{ fontFamily: 'Oswald', fontSize: '20px', marginTop: '20px', textAlign: 'center' }}>
                <p>New here? <Link to="/register" style={{ color: 'orange' }}>Create an account here</Link></p>
                <p>Continue as a <Link to="/search" style={{ color: 'orange' }}>guest here.</Link></p>
            </div>
        </div>
    )
}