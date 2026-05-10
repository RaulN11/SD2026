package cars.microservice.CarsMicroservice.services;

import cars.microservice.CarsMicroservice.models.Car;
import cars.microservice.CarsMicroservice.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarCommandServiceImpl implements CarCommandService {
    private final CarRepository carRepository;

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}