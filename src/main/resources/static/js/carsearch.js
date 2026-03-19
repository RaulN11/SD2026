document.querySelector('.custom-button').addEventListener('click', () =>{
    const brand = document.getElementById('carBrand').value;
    const model = document.getElementById('carModel').value;
    const chassis = document.getElementById('carChassis').value;

    const params = new URLSearchParams();
    if(brand) params.append('brand', brand);
    if(model) params.append('model', model);
    if(chassis) params.append('chassis', chassis);

    fetch(`/ad/api/search?${params.toString()}`,{
        method: 'GET'
    })
    .then(response => {
        if (!response.ok) throw new Error("Search error");
        return response.json();
    })
    .catch(error => console.error('Eroare:', error));
});