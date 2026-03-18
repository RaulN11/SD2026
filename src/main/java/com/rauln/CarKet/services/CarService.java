package com.rauln.CarKet.services;

import com.rauln.CarKet.model.Car;
import com.rauln.CarKet.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    public Car saveCar(Car car){
        return carRepository.save(car);
    }
    public void deleteCar(Long id){
        carRepository.deleteById(id);
    }
    public List<Car> searchCars(String brand, String model, String chassis){
        return carRepository.searchCars(brand, model, chassis);
    }
    public Car findOrCreateCar(Car car){
        List<Car> result = searchCars(car.getBrand(), car.getModel(), car.getChassis());
        if(result.isEmpty()){
            return saveCar(car);
        }
        else{
            return result.get(0);
        }
    }
}
