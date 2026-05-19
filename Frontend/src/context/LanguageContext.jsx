import { createContext, useContext, useState } from 'react'
import en from '../i18n/en.js'
import ro from '../i18n/ro.js'
import zh from '../i18n/chinese.js'

const translations = { en, ro, zh}

const LanguageContext = createContext(null)

export function LanguageProvider({ children }) {
    const [currentLang, setCurrentLang] = useState(() => localStorage.getItem('lang') || 'en')

    const changeLanguage = (newLang) => {
        localStorage.setItem('lang', newLang)
        setCurrentLang(newLang)
    }

    const t = (key) => translations[currentLang][key] ?? key

    return (
        <LanguageContext.Provider value={{ currentLang, changeLanguage, t }}>
            {children}
        </LanguageContext.Provider>
    )
}

export function useLanguage() {
    return useContext(LanguageContext)
}