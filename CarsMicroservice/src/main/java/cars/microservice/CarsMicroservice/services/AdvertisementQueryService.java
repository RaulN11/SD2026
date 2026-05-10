package cars.microservice.CarsMicroservice.services;

import cars.microservice.CarsMicroservice.models.Advertisement;
import java.util.List;

public interface AdvertisementQueryService {
    List<Advertisement> loadAllAds();
    Advertisement loadAdById(Long id);
    List<Advertisement> loadAdsByUser(Long user_id);
    List<Advertisement> loadAdsByCar(String brand, String model, String chassis);
}

