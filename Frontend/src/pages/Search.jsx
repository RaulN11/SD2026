import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { formStyle, inputStyle, labelStyle, buttonStyle, pageWrapper, heroTitle } from '../components/styles'
import { useLanguage } from '../context/LanguageContext'

const CHASSIS_OPTIONS = ['sedan','suv','hatchback','coupe','convertible','wagon','pickup','van']

export default function Search() {
    const [brand, setBrand] = useState('')
    const [model, setModel] = useState('')
    const [chassis, setChassis] = useState('')
    const navigate = useNavigate()
    const { t } = useLanguage()

    const handleSearch = () => {
        const params = new URLSearchParams()
        if (brand) params.append('brand', brand)
        if (model) params.append('model', model)
        if (chassis) params.append('chassis', chassis)
        navigate(`/results?${params}`)
    }

    return (
        <div style={pageWrapper}>
            <h2 style={heroTitle}>
                {t('search_title')} <span style={{ color: 'orange' }}>{t('search_title_accent')}</span>
            </h2>
            <div style={formStyle}>
                <label style={labelStyle}>{t('search_brand')}</label>
                <input style={inputStyle} type="text" placeholder={t('search_brand')} value={brand} onChange={e => setBrand(e.target.value)} />
                <label style={labelStyle}>{t('search_model')}</label>
                <input style={inputStyle} type="text" placeholder={t('search_model')} value={model} onChange={e => setModel(e.target.value)} />
                <label style={labelStyle}>{t('search_chassis')}</label>
                <select style={inputStyle} value={chassis} onChange={e => setChassis(e.target.value)}>
                    <option value="">{t('search_chassis_placeholder')}</option>
                    {CHASSIS_OPTIONS.map(c => (
                        <option key={c} value={c}>{t(`chassis_${c}`)}</option>
                    ))}
                </select>
                <button style={buttonStyle} onClick={handleSearch}>{t('search_button')}</button>
            </div>
        </div>
    )
}