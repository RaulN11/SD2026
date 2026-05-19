import { Link, useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'
import { api } from '../api/api.js'
import { useLanguage } from '../context/LanguageContext'

export default function Navbar() {
    const navigate = useNavigate()
    const [loggedIn, setLoggedIn] = useState(api.isLoggedIn())


    const { currentLang, changeLanguage, t } = useLanguage()

    useEffect(() => {
        const interval = setInterval(() => {
            setLoggedIn(api.isLoggedIn())
        }, 500)
        return () => clearInterval(interval)
    }, [])

    const handleLogout = () => {
        api.logout()
        setLoggedIn(false)
        navigate('/login')
    }
    const flagStyle = (l) => ({
        width: '30px',
        height: '20px',
        cursor: 'pointer',
        borderRadius: '4px',
        objectFit: 'cover',
        border: currentLang === l ? '2px solid rgb(234,123,16)' : '2px solid transparent',
        transition: 'border 0.2s ease'
    })

    return (
        <div style={{
            padding: '15px',
            background: 'linear-gradient(135deg, rgba(64,58,56,0.8) 0%, rgba(70,70,85,0.8) 100%)',
            backdropFilter: 'blur(10px)',
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'space-between',
            position: 'sticky',
            top: 0,
            zIndex: 10
        }}>
            <Link to="/search">
                <h2 style={{ fontSize: '50px', margin: '0 0 0 20px' }}>
                    <span style={{ color: 'orange' }}>Car</span>Ket
                </h2>
            </Link>
            <div style={{ display: 'flex', gap: '50px', paddingRight: '30px', alignItems: 'center' }}>
                {loggedIn && (
                    <Link to="/sell">
                        <i className="fa-solid fa-money-bill" style={{ color: 'rgb(234,123,16)', fontSize: '25px' }}></i>
                    </Link>
                )}
                <Link to="/search">
                    <i className="fa-solid fa-magnifying-glass" style={{ color: 'rgb(234,123,16)', fontSize: '25px' }}></i>
                </Link>
                {loggedIn && (
                    <Link to="/my-ads">
                        <i className="fa-solid fa-user" style={{ color: 'rgb(234,123,16)', fontSize: '25px' }}></i>
                    </Link>
                )}
                <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                    <img
                        src="https://flagcdn.com/gb.svg"
                        alt="English"
                        style={flagStyle('en')}
                        onClick={() => changeLanguage('en')}
                    />
                    <img
                        src="https://flagcdn.com/ro.svg"
                        alt="Romanian"
                        style={flagStyle('ro')}
                        onClick={() => changeLanguage('ro')}
                    />
                    <img
                        src="https://flagcdn.com/cn.svg"
                        alt="Chinese"
                        style={flagStyle('zh')}
                        onClick={() => changeLanguage('zh')}
                    />
                </div>
                {loggedIn && (
                    <button onClick={handleLogout} style={{
                        background: 'transparent',
                        border: 'none',
                        cursor: 'pointer',
                        color: 'white',
                        fontFamily: 'Oswald',
                        fontSize: '18px'
                    }}>{t('nav_logout')}</button>
                )}
            </div>
        </div>
    )
}