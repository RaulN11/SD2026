package cars.microservice.CarsMicroservice.services;

import cars.microservice.CarsMicroservice.models.Car;

public interface CarCommandService {
    Car saveCar(Car car);
    void deleteCar(Long id);
}