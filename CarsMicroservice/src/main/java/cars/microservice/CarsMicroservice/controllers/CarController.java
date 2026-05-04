package cars.microservice.CarsMicroservice.controllers;

import cars.microservice.CarsMicroservice.dtos.CarRequestDTO;
import cars.microservice.CarsMicroservice.dtos.CarResponseDTO;
import cars.microservice.CarsMicroservice.models.Car;
import cars.microservice.CarsMicroservice.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponseDTO addCar(@RequestBody CarRequestDTO carRequestDTO){
        Car car = new Car(carRequestDTO.getBrand(),carRequestDTO.getModel(), carRequestDTO.getChassis());
        Car savedCar = carService.saveCar(car);
        return convertToResponseDTO(savedCar);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCar(@PathVariable Long id){
        carService.deleteCar(id);
    }
    private CarResponseDTO convertToResponseDTO(Car car) {
        CarResponseDTO dto = new CarResponseDTO();
        dto.setId(car.getId());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setChassis(car.getChassis());
        return dto;
    }

}