package com.rauln.CarKet.services;

import com.rauln.CarKet.dto.AdRequestDTO;
import com.rauln.CarKet.model.Advertisement;
import com.rauln.CarKet.model.Car;
import com.rauln.CarKet.model.User;
import com.rauln.CarKet.repositories.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final CarService carService;
    private final UserService userService;

    public Advertisement save_ad(Advertisement advertisement){
        return advertisementRepository.save(advertisement);
    }
    public void delete_ad(Long id){
        advertisementRepository.deleteById(id);
    }
    public List<Advertisement> loadAllAds(){
        return advertisementRepository.findAll();
    }
    public List<Advertisement> loadAdsByUser(Long user_id){
        return advertisementRepository.findAdvertisementsByUserId(user_id);
    }
    public List<Advertisement> loadAdsByCar(String brand, String model, String chassis){
        List<Car> cars = carService.searchCars(brand, model, chassis);
        return advertisementRepository.findAdvertisementsByCarIn(cars);
    }
    public Advertisement publishAd(String email, AdRequestDTO requestBody){
        User user = userService.loadByEmail(email);
        Car car = carService.findOrCreateCar(new Car(
                requestBody.getBrand(),
                requestBody.getModel(),
                requestBody.getChassis()
        ));
        Advertisement advertisement = new Advertisement();
        advertisement.setCar(car);
        advertisement.setUser(user);
        advertisement.setYear(requestBody.getYear());
        advertisement.setPrice(requestBody.getPrice());
        return save_ad(advertisement);
    }

}
