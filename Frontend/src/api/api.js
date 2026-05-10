const USERS_URL = "http://localhost:8081"
const CARS_URL = "http://localhost:8082"

function getToken(){
   return localStorage.getItem("jwt")
}

function authHeaders() {
   return  {'Authorization':`Bearer ${getToken()}`}
}

export const api = {
    login: async (email, password) =>{
        const res = await fetch(`${USERS_URL}/users/api/login`,{
            method : 'POST',
            headers : {'Content-Type': 'application/json'},
            body: JSON.stringify({email, password})
        })
        if(!res.ok) throw new Error('Invalid credentials')
        const token = await res.text()
        localStorage.setItem('jwt', token)
        const payload = JSON.parse(atob(token.split('.')[1]))
        localStorage.setItem('userEmail', payload.sub)
        localStorage.setItem('userRole', payload.role)
        return token
    },

    register: async (data) => {
        const res = await fetch(`${USERS_URL}/users/api/register`,{
            method:'POST',
            headers: {'Content-Type':'application/json'},
            body: JSON.stringify(data)
        })
        if(!res.ok) throw new Error("Registration failed");
        return res.json()
    },

    searchAds: async (brand, model, chassis) =>{
        const params = new URLSearchParams()
        if(brand) params.append('brand', brand)
        if(model) params.append('model', model)
        if(chassis) params.append('chassis', chassis)
        const res = await fetch(`${CARS_URL}/ad/api/search?${params}`,{
            method: 'GET',
        })
        if(!res.ok) throw new Error('Search failed')
        return res.json()
    },

    getAdById: async (id) =>{
        const res = await fetch(`${CARS_URL}/ad/api/search`)
        if(!res.ok) throw new Error("Failed to load ad")
        const ads = await res.json()
        return ads.find(a=>a.id === parseInt(id))
    },

    publishAd: async (adData, imageFile) =>{
        const formData = new FormData()
        formData.append('adData', new Blob([JSON.stringify(adData)],{type:'application/json'}))
        if(imageFile) formData.append('image', imageFile)
        const res = await fetch(`${CARS_URL}/ad/api/publish`,{
            method:'POST',
            headers: authHeaders(),
            body: formData
        })
        if(!res.ok) throw new Error("Could not publish ad!")
        return res.json()
    },

    deleteAd: async (id) =>{
        const res = await fetch(`${CARS_URL}/ad/api/delete/${id}`,{
            method:'DELETE',
            headers: authHeaders()
        })
        if(!res.ok) throw new Error("Could not delete ad!")
    },

    updatePrice: async (id, price) =>{
        const res = await fetch(`${CARS_URL}/ad/api/update/${id}?price=${price}`,{
            method:'PATCH',
            headers: authHeaders()
        })
        if(!res.ok) throw new Error("Could not update price")
        return res.json()
    },

    getMyAds: async () => {
        const res = await fetch(`${CARS_URL}/ad/api/search`, {
            headers: authHeaders()
        })
        if (!res.ok) throw new Error('Failed to load ads')
        const all = await res.json()
        const email = localStorage.getItem('userEmail')
        return all.filter(ad => ad.userEmail === email)
    },

    exportAds: (brand, model, chassis, format) => {
        const params = new URLSearchParams({ format })
        if (brand) params.append('brand', brand)
        if (model) params.append('model', model)
        if (chassis) params.append('chassis', chassis)
        window.open(`${CARS_URL}/ad/api/export?${params}`, '_blank')
    },

    logout: () => {
        localStorage.removeItem('jwt')
        localStorage.removeItem('userEmail')
        localStorage.removeItem('userRole')
    },

    isLoggedIn: () => !!localStorage.getItem('jwt'),
    getEmail: () => localStorage.getItem('userEmail'),
    getRole: () => localStorage.getItem('userRole')
}