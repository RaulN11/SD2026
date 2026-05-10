package cars.microservice.CarsMicroservice.services;

import cars.microservice.CarsMicroservice.dtos.AdRequestDTO;
import cars.microservice.CarsMicroservice.models.Advertisement;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AdvertisementCommandService {
    void deleteAdSecured(Long id, String userEmail, Boolean isAdmin);
    Advertisement publishAd(String email, AdRequestDTO requestBody, MultipartFile image) throws IOException;
    void updateAdPriceSecured(Long id, Integer newPrice, String userEmail);
}
