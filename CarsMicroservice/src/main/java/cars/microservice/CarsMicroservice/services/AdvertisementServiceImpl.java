package cars.microservice.CarsMicroservice.services;
import cars.microservice.CarsMicroservice.dtos.AdRequestDTO;
import cars.microservice.CarsMicroservice.dtos.UserResponseDTO;
import cars.microservice.CarsMicroservice.models.*;
import cars.microservice.CarsMicroservice.repositories.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static cars.microservice.CarsMicroservice.configs.RabbitMQConfig.*;

@RequiredArgsConstructor
@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final CarService carService;
    private final UserClient userClient;
    private final FileStorageService fileStorageService;
    private final RabbitTemplate rabbitTemplate;
    @Override
    public Advertisement saveAd(Advertisement advertisement){
        return advertisementRepository.save(advertisement);
    }

    @Override
    public void deleteAdSecured(Long id, String userEmail, Boolean isAdmin){
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        if(advertisement.getUserEmail().equals(userEmail) || isAdmin) {
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
        UserResponseDTO user = userClient.getUserByEmail(email);
        Car car = carService.findOrCreateCar(new Car(
                requestBody.getBrand(),
                requestBody.getModel(),
                requestBody.getChassis()
        ));
        Advertisement advertisement = new Advertisement();
        advertisement.setUserId(user.getId());
        advertisement.setUserEmail(user.getEmail());
        advertisement.setFirstName(user.getFirstName());
        advertisement.setLastName(user.getLastName());
        advertisement.setCar(car);
        advertisement.setYear(requestBody.getYear());
        advertisement.setPrice(requestBody.getPrice());

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.saveFile(image);
            advertisement.setImages(List.of(imageUrl));
        }
        AdCreatedEvent event = new AdCreatedEvent(email, car.getBrand(), car.getModel(), advertisement.getPrice());
        Advertisement saved = advertisementRepository.save(advertisement);
        rabbitTemplate.convertAndSend(AD_EXCHANGE, AD_CREATED_KEY, event);
        return saved;
    }

    @Override
    public void updateAdPriceSecured(Long id, Integer newPrice, String userEmail) {
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        if (advertisement.getUserEmail().equals(userEmail) ) {
            advertisement.setPrice(newPrice);
            advertisementRepository.save(advertisement);
        } else {
            throw new AccessDeniedException("You can only edit your own ads.");
        }
    }
}