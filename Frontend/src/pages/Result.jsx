import { useEffect, useState } from 'react'
import { useSearchParams, Link } from 'react-router-dom'
import { api } from '../api/api.js'
import {useLanguage} from "../context/LanguageContext.jsx";

export default function Results() {
    const [searchParams] = useSearchParams()
    const [ads, setAds] = useState([])
    const [loading, setLoading] = useState(true)
    const [showExport, setShowExport] = useState(false)
    const [format, setFormat] = useState('')
    const { t } = useLanguage()

    const brand = searchParams.get('brand') || ''
    const model = searchParams.get('model') || ''
    const chassis = searchParams.get('chassis') || ''

    useEffect(() => {
        api.searchAds(brand, model, chassis)
            .then(setAds)
            .finally(() => setLoading(false))
    }, [brand, model, chassis])

    const handleExport = () => {
        if (!format) { alert(t('results_export_select')); return }
        api.exportAds(brand, model, chassis, format)
        setShowExport(false)
    }

    return (
        <div>
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <h2 style={{ fontFamily: 'Oswald', fontSize: '40px', margin: '20px 0' }}>
                    {t('results_title')} <span style={{ color: 'orange' }}>{t('results_title_accent')}</span>
                </h2>
                <button onClick={() => setShowExport(true)} style={{
                    fontFamily: 'Oswald', cursor: 'pointer', backgroundColor: 'orange',
                    borderRadius: '10px', fontSize: '20px', color: 'rgb(74,72,72)',
                    border: 'none', padding: '5px 10px'
                }}>{t('results_export')}</button>
            </div>

            {loading && <p style={{ textAlign: 'center', fontFamily: 'Oswald', fontSize: '24px', marginTop: '40px' }}>{t('results_loading')}</p>}
            {!loading && ads.length === 0 && (
                <p style={{ textAlign: 'center', fontFamily: 'Oswald', fontSize: '24px', marginTop: '40px' }}>{t('results_none')}</p>
            )}

            <div style={{
                display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))',
                gap: '30px', padding: '20px 5%', marginTop: '20px'
            }}>
                {ads.map(ad => (
                    <Link key={ad.id} to={`/ad/${ad.id}`} style={{ textDecoration: 'none', color: 'white' }}>
                        <div style={{
                            backgroundColor: 'rgba(20,20,20,0.6)', backdropFilter: 'blur(15px)',
                            border: '1px solid rgba(255,255,255,0.1)', borderRadius: '12px',
                            padding: '20px', boxShadow: '0 8px 32px rgba(0,0,0,0.3)',
                            transition: 'transform 0.3s ease'
                        }}
                             onMouseEnter={e => e.currentTarget.style.transform = 'translateY(-5px)'}
                             onMouseLeave={e => e.currentTarget.style.transform = 'translateY(0)'}
                        >
                            <h3 style={{ color: 'orange', marginBottom: '15px', fontSize: '24px' }}>
                                {ad.car?.brand} {ad.car?.model}
                            </h3>
                            {ad.images && ad.images.length > 0 && (
                                <img src={`http://localhost:8082${ad.images[0]}`} alt="Car"
                                     style={{ width: '100%', aspectRatio: '16/9', objectFit: 'cover', borderRadius: '6px', marginBottom: '10px' }} />
                            )}
                            <p style={{ fontFamily: 'Oswald', fontSize: '18px' }}>{ad.price}$</p>
                            <p style={{ fontFamily: 'Oswald', fontSize: '16px', color: '#ccc' }}>{ad.year}</p>
                        </div>
                    </Link>
                ))}
            </div>

            {showExport && (
                <div style={{
                    position: 'fixed', top: 0, left: 0, width: '100vw', height: '100vh',
                    backgroundColor: 'rgba(0,0,0,0.4)', backdropFilter: 'blur(8px)',
                    zIndex: 999, display: 'flex', justifyContent: 'center', alignItems: 'center'
                }}>
                    <div style={{
                        display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center',
                        width: '50%', backgroundColor: 'rgba(0,0,0,0.9)', borderRadius: '20px', padding: '40px',
                        border: '1px solid rgba(255,255,255,0.1)'
                    }}>
                        <button onClick={() => setShowExport(false)} style={{
                            background: 'transparent', color: 'orange', border: 'none',
                            fontSize: '40px', alignSelf: 'flex-end', cursor: 'pointer'
                        }}>&times;</button>
                        <h2 style={{ fontFamily: 'Oswald', fontSize: '30px', marginBottom: '30px' }}>{t('results_export_title')}</h2>
                        <div style={{ display: 'flex', gap: '30px', marginBottom: '40px', fontFamily: 'Oswald', fontSize: '20px' }}>
                            {['JSON', 'XML', 'CSV'].map(f => (
                                <label key={f} style={{ cursor: 'pointer', display: 'flex', alignItems: 'center', gap: '8px' }}>
                                    <input type="radio" name="format" value={f} onChange={() => setFormat(f)} />
                                    {f}
                                </label>
                            ))}
                        </div>
                        <button onClick={handleExport} style={{
                            fontFamily: 'Oswald', cursor: 'pointer', backgroundColor: 'orange',
                            borderRadius: '10px', fontSize: '20px', color: 'rgb(74,72,72)',
                            border: 'none', padding: '8px 20px'
                        }}>{t('results_export_button')}</button>
                    </div>
                </div>
            )}
        </div>
    )
}