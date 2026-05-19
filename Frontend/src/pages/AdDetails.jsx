import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { api } from '../api/api.js'
import { useLanguage } from "../context/LanguageContext.jsx"
import { formStyle, inputStyle, labelStyle, buttonStyle } from '../components/styles'

export default function AdDetails() {
    const { id } = useParams()
    const navigate = useNavigate()
    const [ad, setAd] = useState(null)
    const [loading, setLoading] = useState(true)
    const [showModal, setShowModal] = useState(false)
    const [newPrice, setNewPrice] = useState('')
    const [error, setError] = useState('')
    const { t } = useLanguage()

    const email = api.getEmail()
    const role = api.getRole()
    const isOwner = ad && ad.userEmail === email
    const isAdmin = role === 'ROLE_ADMIN'

    useEffect(() => {
        api.getAdById(id)
            .then(data => {
                setAd(data)
                setNewPrice(data?.price || '')
            })
            .finally(() => setLoading(false))
    }, [id])

    const handleDelete = async () => {
        if (!confirm(t('ad_delete_confirm'))) return
        try {
            await api.deleteAd(id)
            navigate('/search')
        } catch {
            setError(t('ad_error_delete'))
        }
    }

    const handleUpdatePrice = async () => {
        try {
            await api.updatePrice(id, parseInt(newPrice))
            setAd({ ...ad, price: parseInt(newPrice) })
            setShowModal(false)
        } catch {
            setError(t('ad_error_price'))
        }
    }

    if (loading) return <p style={{ textAlign: 'center', fontFamily: 'Oswald', fontSize: '24px', marginTop: '40px' }}>{t('ad_loading')}</p>
    if (!ad) return <p style={{ textAlign: 'center', fontFamily: 'Oswald', fontSize: '24px', marginTop: '40px' }}>{t('ad_not_found')}</p>

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <div style={{ ...formStyle, maxWidth: '500px', width: '90%', textAlign: 'center', marginBottom: '40px' }}>
                <h2 style={{ color: 'orange', marginBottom: '20px', fontSize: '32px' }}>
                    {ad.car?.brand} {ad.car?.model}
                </h2>

                {ad.images && ad.images.length > 0 && (
                    <img
                        src={`http://localhost:8082${ad.images[0]}`}
                        alt="Car"
                        style={{ width: '100%', borderRadius: '8px', marginBottom: '20px' }}
                    />
                )}

                <p style={{ fontFamily: 'Oswald', fontSize: '20px', marginBottom: '10px' }}>
                    <span style={{ color: 'orange' }}>{t('ad_year')}:</span> {ad.year}
                </p>
                <p style={{ fontFamily: 'Oswald', fontSize: '20px', marginBottom: '10px' }}>
                    <span style={{ color: 'orange' }}>{t('ad_price')}:</span> ${ad.price}
                </p>
                <p style={{ fontFamily: 'Oswald', fontSize: '20px', marginBottom: '10px' }}>
                    <span style={{ color: 'orange' }}>{t('ad_chassis')}:</span> {ad.car?.chassis}
                </p>
                <p style={{ fontFamily: 'Oswald', fontSize: '18px', marginBottom: '20px', color: '#ccc' }}>
                    {t('ad_seller')}: {ad.firstName} {ad.lastName}
                </p>

                {error && <p style={{ color: 'red', marginBottom: '10px' }}>{error}</p>}

                {(isOwner || isAdmin) && (
                    <div style={{ display: 'flex', justifyContent: 'space-around', marginTop: '20px', gap: '20px' }}>
                        {isOwner && (
                            <button style={buttonStyle} onClick={() => setShowModal(true)}>
                                {t('ad_edit_price')}
                            </button>
                        )}
                        <button
                            style={{ ...buttonStyle, backgroundColor: '#ff4444' }}
                            onClick={handleDelete}
                        >
                            {t('ad_delete')}
                        </button>
                    </div>
                )}
            </div>

            {showModal && (
                <div style={{
                    position: 'fixed', top: 0, left: 0, width: '100vw', height: '100vh',
                    backgroundColor: 'rgba(0,0,0,0.6)', backdropFilter: 'blur(5px)',
                    zIndex: 999, display: 'flex', justifyContent: 'center', alignItems: 'center'
                }}>
                    <div style={{ ...formStyle, width: '400px', position: 'relative' }}>
                        <button onClick={() => setShowModal(false)} style={{
                            position: 'absolute', top: '15px', right: '25px',
                            background: 'transparent', border: 'none', color: 'white',
                            fontSize: '28px', cursor: 'pointer'
                        }}>&times;</button>
                        <h2 style={{ color: 'orange', marginBottom: '20px' }}>{t('ad_edit_price')}</h2>
                        <label style={labelStyle}>{t('ad_new_price')}</label>
                        <input
                            style={inputStyle}
                            type="number"
                            value={newPrice}
                            onChange={e => setNewPrice(e.target.value)}
                        />
                        <button style={{ ...buttonStyle, width: '100%' }} onClick={handleUpdatePrice}>
                            {t('ad_save')}
                        </button>
                    </div>
                </div>
            )}
        </div>
    )
}