import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { api } from '../api/api.js'
import { formStyle, inputStyle, labelStyle, buttonStyle, pageWrapper, heroTitle } from '../components/styles.js'
import { useLanguage } from '../context/LanguageContext'

export default function Login() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const navigate = useNavigate()
    const { t } = useLanguage()

    const handleLogin = async () => {
        try {
            await api.login(email, password)
            navigate('/search')
        } catch {
            setError(t('login_error'))
        }
    }

    return (
        <div style={pageWrapper}>
            <h2 style={heroTitle}>
                {t('login_title')} <span style={{ color: 'orange' }}>CarKet</span>
            </h2>
            <div style={formStyle}>
                <label style={labelStyle}>{t('login_email')}</label>
                <input
                    style={inputStyle}
                    type="email"
                    placeholder={t('login_email')}
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    onKeyDown={e => e.key === 'Enter' && handleLogin()}
                />
                <label style={labelStyle}>{t('login_password')}</label>
                <input
                    style={inputStyle}
                    type="password"
                    placeholder={t('login_password')}
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    onKeyDown={e => e.key === 'Enter' && handleLogin()}
                />
                {error && <p style={{ color: 'red', marginBottom: '10px' }}>{error}</p>}
                <button style={buttonStyle} onClick={handleLogin}>{t('login_button')}</button>
            </div>
            <div style={{ fontFamily: 'Oswald', fontSize: '20px', marginTop: '20px', textAlign: 'center' }}>
                <p>{t('login_new')} <Link to="/register" style={{ color: 'orange' }}>{t('login_create')}</Link></p>
                <p>{t('login_guest')} <Link to="/search" style={{ color: 'orange' }}>{t('login_guest_link')}</Link></p>
            </div>
        </div>
    )
}