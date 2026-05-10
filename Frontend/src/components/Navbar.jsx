import { Link, useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'
import { api } from '../api/api.js'

export default function Navbar() {
    const navigate = useNavigate()
    const [loggedIn, setLoggedIn] = useState(api.isLoggedIn())

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
                {loggedIn && (
                    <button onClick={handleLogout} style={{
                        background: 'transparent',
                        border: 'none',
                        cursor: 'pointer',
                        color: 'white',
                        fontFamily: 'Oswald',
                        fontSize: '18px'
                    }}>Logout</button>
                )}
            </div>
        </div>
    )
}