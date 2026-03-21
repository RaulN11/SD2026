document.querySelector('.custom-button').addEventListener('click', () =>{
    const brand = document.getElementById('carBrand').value;
    const model = document.getElementById('carModel').value;
    const chassis = document.getElementById('carChassis').value;
    const year = document.getElementById('carYear').value;
    const price = document.getElementById('carPrice').value;
    if(!brand || !model || !chassis || !year || !price){
        alert("Please fill all fields!");
        return;
    }
    const carData ={
        brand: brand,
        model: model,
        chassis: chassis,
        year: year,
        price: price
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