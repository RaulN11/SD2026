document.querySelector('.custom-button').addEventListener('click', () =>{
    const carData = {
        brand: document.getElementById('carBrand').value,
        model: document.getElementById('carModel').value,
        chassis: document.getElementById('carChassis').value,
        year: parseInt(document.getElementById('carYear').value),
        price: parseInt(document.getElementById('carPrice').value)
    }

    const formData = new FormData();
    formData.append('adData', new Blob([JSON.stringify(carData)], {type: 'application/json'}));

    const imageFile = document.getElementById('carImage').files[0];
    if(imageFile){
        formData.append('image', imageFile);
    }
    fetch('/ad/api/publish',{
        method:'POST',
        body: formData
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