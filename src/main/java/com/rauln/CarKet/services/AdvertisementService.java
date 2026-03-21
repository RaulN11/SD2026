package com.rauln.CarKet.services;

import com.rauln.CarKet.dto.AdRequestDTO;
import com.rauln.CarKet.model.Advertisement;
import com.rauln.CarKet.model.Car;
import com.rauln.CarKet.model.User;
import com.rauln.CarKet.repositories.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final CarService carService;
    private final UserService userService;

    public Advertisement saveAd(Advertisement advertisement){
        return advertisementRepository.save(advertisement);
    }
    public void deleteAdSecured(Long id, String userEmail, Boolean isAdmin){
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        if(advertisement.getUser().getEmail().equals(userEmail) || isAdmin) {
            advertisementRepository.deleteById(id);
        }else {
            throw new AccessDeniedException("You can only delete your own ads.");
        }
    }
    public List<Advertisement> loadAllAds(){
        return advertisementRepository.findAll();
    }
    public Advertisement loadAdById(Long id){
        return advertisementRepository.findAdvertisementById(id);
    }
    public List<Advertisement> loadAdsByUser(Long user_id){
        return advertisementRepository.findAdvertisementsByUserId(user_id);
    }
    public List<Advertisement> loadAdsByCar(String brand, String model, String chassis){
        List<Car> cars = carService.searchCars(brand, model, chassis);
        return advertisementRepository.findAdvertisementsByCarIn(cars);
    }
    public Advertisement publishAd(String email, AdRequestDTO requestBody, MultipartFile image) throws IOException {
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

        if(image != null && !image.isEmpty()){
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/uploads/");
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath);
            advertisement.setImages(List.of("/uploads/" + fileName));
        }

        return saveAd(advertisement);
    }

    public void updateAdPriceSecured(Long id, Integer newPrice, String userEmail) {
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        if (advertisement.getUser().getEmail().equals(userEmail) ) {
            advertisement.setPrice(newPrice);
            advertisementRepository.save(advertisement);
        } else {
            throw new AccessDeniedException("You can only edit your own ads.");
        }
    }

}
