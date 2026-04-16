package com.rauln.CarKet.services;

import com.rauln.CarKet.dto.AdRequestDTO;
import com.rauln.CarKet.model.*;
import com.rauln.CarKet.repositories.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.rauln.CarKet.configurations.RabbitMQConfig.*;

@RequiredArgsConstructor
@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final CarService carService;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Advertisement saveAd(Advertisement advertisement){
        return advertisementRepository.save(advertisement);
    }

    @Override
    public void deleteAdSecured(Long id, String userEmail, Boolean isAdmin){
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        if(advertisement.getUser().getEmail().equals(userEmail) || isAdmin) {
            advertisementRepository.deleteById(id);
        }else {
            throw new AccessDeniedException("You can only delete your own ads.");
        }
        AdDeletedEvent event = new AdDeletedEvent(userEmail, advertisement.getCar().getBrand(), advertisement.getCar().getModel());
        rabbitTemplate.convertAndSend(AD_EXCHANGE, AD_DELETED_KEY, event);
    }

    @Override
    public List<Advertisement> loadAllAds(){
        return advertisementRepository.findAll();
    }

    @Override
    public Advertisement loadAdById(Long id){
        return advertisementRepository.findAdvertisementById(id);
    }

    @Override
    public List<Advertisement> loadAdsByUser(Long user_id){
        return advertisementRepository.findAdvertisementsByUserId(user_id);
    }

    @Override
    public List<Advertisement> loadAdsByCar(String brand, String model, String chassis){
        List<Car> cars = carService.searchCars(brand, model, chassis);
        return advertisementRepository.findAdvertisementsByCarIn(cars);
    }

    @Override
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

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.saveFile(image);
            advertisement.setImages(List.of(imageUrl));
        }
        AdCreatedEvent event = new AdCreatedEvent(email, car.getBrand(), car.getModel(), advertisement.getPrice());
        rabbitTemplate.convertAndSend(AD_EXCHANGE, AD_CREATED_KEY, event);
        return saveAd(advertisement);
    }

    @Override
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