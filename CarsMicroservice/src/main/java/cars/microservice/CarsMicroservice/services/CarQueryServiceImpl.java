package cars.microservice.CarsMicroservice.services;

import cars.microservice.CarsMicroservice.models.Car;
import cars.microservice.CarsMicroservice.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarQueryServiceImpl implements CarQueryService {
    private final CarRepository carRepository;
    private final CarCommandService carCommandService;

    @Override
    public List<Car> searchCars(String brand, String model, String chassis) {
        return carRepository.searchCars(brand, model, chassis);
    }

    @Override
    public Car findOrCreateCar(Car car) {
        List<Car> result = searchCars(car.getBrand(), car.getModel(), car.getChassis());
        if (result.isEmpty()) {
            return carCommandService.saveCar(car);
        }
        return result.get(0);
    }
}