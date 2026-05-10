package cars.microservice.CarsMicroservice.services;

import cars.microservice.CarsMicroservice.models.Advertisement;
import cars.microservice.CarsMicroservice.models.Car;
import cars.microservice.CarsMicroservice.repositories.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AdvertisementQueryServiceImpl implements  AdvertisementQueryService{
    private final AdvertisementRepository advertisementRepository;
    private final CarQueryService carQueryService;

    @Override
    public Advertisement loadAdById(Long id) {
        return advertisementRepository.findAdvertisementById(id);
    }

    @Override
    public List<Advertisement> loadAllAds() {
        return advertisementRepository.findAll();
    }

    @Override
    public List<Advertisement> loadAdsByUser(Long userId) {
        return advertisementRepository.findAdvertisementsByUserId(userId);
    }

    @Override
    public List<Advertisement> loadAdsByCar(String brand, String model, String chassis) {
        List<Car> cars = carQueryService.searchCars(brand, model, chassis);
        return advertisementRepository.findAdvertisementsByCarIn(cars);
    }
}
