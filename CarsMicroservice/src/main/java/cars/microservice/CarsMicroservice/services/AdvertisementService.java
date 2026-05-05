package cars.microservice.CarsMicroservice.services;

import cars.microservice.CarsMicroservice.models.Advertisement;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdvertisementService {
    Advertisement saveAd(Advertisement advertisement);
    void deleteAdSecured(Long id, String userEmail, Boolean isAdmin);
    List<Advertisement> loadAllAds();
    Advertisement loadAdById(Long id);
    List<Advertisement> loadAdsByUser(Long user_id);
    List<Advertisement> loadAdsByCar(String brand, String model, String chassis);
    Advertisement publishAd(String email, AdRequestDTO requestBody, MultipartFile image) throws IOException;
    void updateAdPriceSecured(Long id, Integer newPrice, String userEmail);
}
