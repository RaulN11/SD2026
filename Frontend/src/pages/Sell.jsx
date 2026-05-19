import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { api } from '../api/api.js'
import { formStyle, inputStyle, labelStyle, buttonStyle, pageWrapper, heroTitle } from '../components/styles'
import { useLanguage } from '../context/LanguageContext'

const CHASSIS_OPTIONS = ['sedan', 'suv', 'hatchback', 'coupe', 'convertible', 'wagon', 'pickup', 'van']

export default function Sell() {
    const [form, setForm] = useState({ brand: '', model: '', chassis: '', year: '', price: '' })
    const [image, setImage] = useState(null)
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState('')
    const navigate = useNavigate()
    const { t } = useLanguage()

    const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value })

    const handlePublish = async () => {
        if (!form.brand || !form.model || !form.chassis || !form.year || !form.price) {
            setError(t('sell_error_fields'))
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
            alert(t('sell_success'))
            navigate('/search')
        } catch {
            setError(t('sell_error_failed'))
        } finally {
            setLoading(false)
        }
    }

    return (
        <div style={pageWrapper}>
            <h2 style={heroTitle}>
                {t('sell_title')} <span style={{ color: 'orange' }}>{t('sell_title_accent')}</span> {t('sell_title_end')}
            </h2>
            <div style={formStyle}>
                <label style={labelStyle}>{t('sell_brand')}</label>
                <input style={inputStyle} type="text" name="brand" placeholder={t('sell_brand')} onChange={handleChange} />
                <label style={labelStyle}>{t('sell_model')}</label>
                <input style={inputStyle} type="text" name="model" placeholder={t('sell_model')} onChange={handleChange} />
                <label style={labelStyle}>{t('sell_chassis')}</label>
                <select style={inputStyle} name="chassis" onChange={handleChange}>
                    <option value="">{t('search_chassis_placeholder')}</option>
                    {CHASSIS_OPTIONS.map(c => (
                        <option key={c} value={c}>{t(`chassis_${c}`)}</option>
                    ))}
                </select>
                <label style={labelStyle}>{t('sell_year')}</label>
                <input style={inputStyle} type="number" name="year" placeholder={t('sell_year')} onChange={handleChange} />
                <label style={labelStyle}>{t('sell_price')}</label>
                <input style={inputStyle} type="number" name="price" placeholder={t('sell_price')} onChange={handleChange} />
                <label style={labelStyle}>{t('sell_image')}</label>
                <input style={inputStyle} type="file" accept="image/*" onChange={e => setImage(e.target.files[0])} />
                {error && <p style={{ color: 'red', marginBottom: '10px' }}>{error}</p>}
                <button style={buttonStyle} onClick={handlePublish} disabled={loading}>
                    {loading ? t('sell_publishing') : t('sell_button')}
                </button>
            </div>
        </div>
    )
}