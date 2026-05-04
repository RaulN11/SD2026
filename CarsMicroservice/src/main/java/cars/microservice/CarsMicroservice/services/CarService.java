package cars.microservice.CarsMicroservice.services;

import cars.microservice.CarsMicroservice.models.Car;

import java.util.List;

public interface CarService {
    Car saveCar(Car car);
    void deleteCar(Long id);
    List<Car> searchCars(String brand, String model, String chassis);
    Car findOrCreateCar(Car car);
}
