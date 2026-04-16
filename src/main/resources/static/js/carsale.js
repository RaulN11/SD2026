const publishButton = document.querySelector('.custom-button');
if(publishButton){
    publishButton.addEventListener('click', ()=>{
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
}
const exportFirstButton = document.getElementById("export-first");
const exportModalButton = document.getElementById("export-modal-button");
const closeButton = document.getElementById("close-button");
const exportModal=document.getElementById("modal-overlay");
function openExportModal(){
    exportModal.style.display='flex';
}
function closeExportModal(){
    exportModal.style.display='none';
}
exportFirstButton.addEventListener('click', ()=>{
    openExportModal();
})
closeButton.addEventListener('click', ()=>{
    closeExportModal();
})
exportModalButton.addEventListener('click', (e)=>{
    const selectedType = document.querySelector('input[name="format"]:checked');
    if(!selectedType){
        alert("Please select an exporting format!");
        return;
    }
    const format = selectedType.value.toLowerCase();
    const urlParams = new URLSearchParams(window.location.search);
    const brand = urlParams.get('brand') || '';
    const model = urlParams.get('model') || '';
    const chassis = urlParams.get('chassis') || '';
    window.location.href=`/ad/api/export?format=${format}&brand=${brand}&model=${model}&chassis=${chassis}`;
})