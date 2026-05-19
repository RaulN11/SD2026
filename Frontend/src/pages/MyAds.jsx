import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { api } from '../api/api.js'
import { useLanguage } from "../context/LanguageContext.jsx"

export default function MyAds() {
    const [ads, setAds] = useState([])
    const [loading, setLoading] = useState(true)
    const { t } = useLanguage()

    useEffect(() => {
        api.getMyAds()
            .then(setAds)
            .finally(() => setLoading(false))
    }, [])

    if (loading) return (
        <p style={{ textAlign: 'center', fontFamily: 'Oswald', fontSize: '24px', marginTop: '40px' }}>
            {t('myads_loading')}
        </p>
    )

    return (
        <div>
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <h2 style={{ fontFamily: 'Oswald', fontSize: '40px', margin: '20px 0' }}>
                    {t('myads_title')} <span style={{ color: 'orange' }}>{t('myads_title_accent')}</span>
                </h2>
            </div>

            {ads.length === 0 && (
                <p style={{ textAlign: 'center', fontFamily: 'Oswald', fontSize: '24px', marginTop: '40px' }}>
                    {t('myads_none')} <Link to="/sell" style={{ color: 'orange' }}>{t('myads_create')}</Link>
                </p>
            )}

            <div style={{
                display: 'grid',
                gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))',
                gap: '30px',
                padding: '20px 5%',
                marginTop: '20px'
            }}>
                {ads.map(ad => (
                    <Link key={ad.id} to={`/ad/${ad.id}`} style={{ textDecoration: 'none', color: 'white' }}>
                        <div
                            style={{
                                backgroundColor: 'rgba(20,20,20,0.6)',
                                backdropFilter: 'blur(15px)',
                                border: '1px solid rgba(255,255,255,0.1)',
                                borderRadius: '12px',
                                padding: '20px',
                                boxShadow: '0 8px 32px rgba(0,0,0,0.3)',
                                transition: 'transform 0.3s ease'
                            }}
                            onMouseEnter={e => e.currentTarget.style.transform = 'translateY(-5px)'}
                            onMouseLeave={e => e.currentTarget.style.transform = 'translateY(0)'}
                        >
                            <h3 style={{ color: 'orange', marginBottom: '15px', fontSize: '24px' }}>
                                {ad.car?.brand} {ad.car?.model}
                            </h3>
                            {ad.images && ad.images.length > 0 && (
                                <img
                                    src={`http://localhost:8082${ad.images[0]}`}
                                    alt="Car"
                                    style={{
                                        width: '100%',
                                        aspectRatio: '16/9',
                                        objectFit: 'cover',
                                        borderRadius: '6px',
                                        marginBottom: '10px'
                                    }}
                                />
                            )}
                            <p style={{ fontFamily: 'Oswald', fontSize: '18px' }}>{ad.price}$</p>
                            <p style={{ fontFamily: 'Oswald', fontSize: '16px', color: '#ccc' }}>{ad.year}</p>
                            <p style={{ fontFamily: 'Oswald', fontSize: '14px', color: '#aaa' }}>{ad.car?.chassis}</p>
                        </div>
                    </Link>
                ))}
            </div>
        </div>
    )
}