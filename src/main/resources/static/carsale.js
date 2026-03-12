document.querySelector('.publish-button').addEventListener('click', () =>{
    const carData = {
        brand: document.getElementById('carBrand').value,
        model: document.getElementById('carModel').value,
        chassis: document.getElementById('carChassis').value,
        year: parseInt(document.getElementById('carYear').value),
        price: parseInt(document.getElementById('carPrice').value)
    }
    fetch('/car/api/add',{
        method:'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body:JSON.stringify(carData)
    })
    .then(response => {
        if(response.ok){
            alert("Car published");
        }else {
            alert("Failed to publish car");
        }
    })
    .catch(error => console.error('Error', error));
});