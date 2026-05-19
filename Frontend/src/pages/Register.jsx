import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { api } from '../api/api.js'
import { formStyle, inputStyle, labelStyle, buttonStyle, pageWrapper, heroTitle } from '../components/styles.js'
import { useLanguage } from '../context/LanguageContext'

export default function Register() {
    const [form, setForm] = useState({ firstName: '', lastName: '', email: '', password: '', role: 'client' })
    const [error, setError] = useState('')
    const navigate = useNavigate()
    const { t } = useLanguage()

    const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value })

    const handleRegister = async () => {
        if (!form.firstName || !form.lastName || !form.email || !form.password) {
            setError(t('register_error_fields'))
            return
        }
        try {
            await api.register(form)
            navigate('/login')
        } catch {
            setError(t('register_error_failed'))
        }
    }

    return (
        <div style={pageWrapper}>
            <h2 style={heroTitle}>
                {t('register_title')} <span style={{ color: 'orange' }}>CarKet</span>
            </h2>
            <div style={formStyle}>
                <label style={labelStyle}>{t('register_firstname')}</label>
                <input style={inputStyle} type="text" name="firstName" placeholder={t('register_firstname')} onChange={handleChange} />
                <label style={labelStyle}>{t('register_lastname')}</label>
                <input style={inputStyle} type="text" name="lastName" placeholder={t('register_lastname')} onChange={handleChange} />
                <label style={labelStyle}>{t('register_email')}</label>
                <input style={inputStyle} type="email" name="email" placeholder={t('register_email')} onChange={handleChange} />
                <label style={labelStyle}>{t('register_role')}</label>
                <select style={inputStyle} name="role" onChange={handleChange}>
                    <option value="client">{t('register_role_client')}</option>
                    <option value="admin">{t('register_role_admin')}</option>
                </select>
                <label style={labelStyle}>{t('register_password')}</label>
                <input style={inputStyle} type="password" name="password" placeholder={t('register_password')} onChange={handleChange} />
                {error && <p style={{ color: 'red', marginBottom: '10px' }}>{error}</p>}
                <button style={buttonStyle} onClick={handleRegister}>{t('register_button')}</button>
            </div>
            <div style={{ fontFamily: 'Oswald', fontSize: '20px', marginTop: '20px' }}>
                <p>{t('register_have_account')} <Link to="/login" style={{ color: 'orange' }}>{t('register_login_link')}</Link></p>
            </div>
        </div>
    )
}