package cars.microservice.CarsMicroservice.services;

import cars.microservice.CarsMicroservice.dtos.AdRequestDTO;
import cars.microservice.CarsMicroservice.dtos.UserResponseDTO;
import cars.microservice.CarsMicroservice.models.*;
import cars.microservice.CarsMicroservice.repositories.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static cars.microservice.CarsMicroservice.configs.RabbitMQConfig.*;

@Service
@RequiredArgsConstructor
public class AdvertisementCommandServiceImpl implements AdvertisementCommandService {
    private final AdvertisementRepository advertisementRepository;
    private final CarQueryService carQueryService;
    private final UserClient userClient;
    private final FileStorageService fileStorageService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Advertisement publishAd(String email, AdRequestDTO requestBody, MultipartFile image) throws IOException {
        UserResponseDTO user = userClient.getUserByEmail(email);
        Car car = carQueryService.findOrCreateCar(new Car(
                requestBody.getBrand(), requestBody.getModel(), requestBody.getChassis()));
        Advertisement advertisement = new Advertisement();
        advertisement.setUserId(user.getId());
        advertisement.setUserEmail(user.getEmail());
        advertisement.setFirstName(user.getFirstName());
        advertisement.setLastName(user.getLastName());
        advertisement.setCar(car);
        advertisement.setYear(requestBody.getYear());
        advertisement.setPrice(requestBody.getPrice());
        if (image != null && !image.isEmpty()) {
            advertisement.setImages(List.of(fileStorageService.saveFile(image)));
        }
        Advertisement saved = advertisementRepository.save(advertisement);
        rabbitTemplate.convertAndSend(AD_EXCHANGE, AD_CREATED_KEY,
                new AdCreatedEvent(email, car.getBrand(), car.getModel(), saved.getPrice()));
        return saved;
    }

    @Override
    public void deleteAdSecured(Long id, String userEmail, Boolean isAdmin) {
        Advertisement ad = advertisementRepository.findAdvertisementById(id);
        if (ad == null) throw new RuntimeException("Ad not found");
        if (!ad.getUserEmail().equals(userEmail) && !isAdmin)
            throw new AccessDeniedException("You can only delete your own ads.");
        advertisementRepository.deleteById(id);
        rabbitTemplate.convertAndSend(AD_EXCHANGE, AD_DELETED_KEY,
                new AdDeletedEvent(userEmail, ad.getCar().getBrand(), ad.getCar().getModel()));
    }

    @Override
    public void updateAdPriceSecured(Long id, Integer newPrice, String userEmail) {
        Advertisement ad = advertisementRepository.findAdvertisementById(id);
        if (ad == null) throw new RuntimeException("Ad not found");
        if (!ad.getUserEmail().equals(userEmail))
            throw new AccessDeniedException("You can only edit your own ads.");
        ad.setPrice(newPrice);
        advertisementRepository.save(ad);
    }
}