package cars.microservice.CarsMicroservice.services;

import cars.microservice.CarsMicroservice.models.Car;
import cars.microservice.CarsMicroservice.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> searchCars(String brand, String model, String chassis) {
        return carRepository.searchCars(brand, model, chassis);
    }

    @Override
    public Car findOrCreateCar(Car car) {
        List<Car> result = searchCars(car.getBrand(), car.getModel(), car.getChassis());
        if (result.isEmpty()) {
            return saveCar(car);
        } else {
            return result.get(0);
        }
    }
}