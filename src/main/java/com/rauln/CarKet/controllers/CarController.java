package com.rauln.CarKet.controllers;

import com.rauln.CarKet.model.Car;
import com.rauln.CarKet.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car/api")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/add")
    public Car addCar(@RequestBody Car car){
        return carService.saveCar(car);
    }

    @GetMapping("/search")
    public List<Car> searchCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String chassis,
            @RequestParam(required = false) Integer year
    ){
        return carService.searchCars(brand, model, chassis, year);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCar(@PathVariable Long id){
        carService.deleteCar(id);
    }

}
