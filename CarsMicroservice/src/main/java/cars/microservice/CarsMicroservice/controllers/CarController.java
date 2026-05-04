package cars.microservice.CarsMicroservice.controllers;

import cars.microservice.CarsMicroservice.models.Car;
import cars.microservice.CarsMicroservice.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/add")
    public Car addCar(@RequestBody Car car){
        return carService.saveCar(car);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCar(@PathVariable Long id){
        carService.deleteCar(id);
    }

}