package cars.microservice.CarsMicroservice.controllers;

import cars.microservice.CarsMicroservice.dtos.CarRequestDTO;
import cars.microservice.CarsMicroservice.dtos.CarResponseDTO;
import cars.microservice.CarsMicroservice.models.Car;
import cars.microservice.CarsMicroservice.services.CarCommandService;
import cars.microservice.CarsMicroservice.services.CarQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarCommandService carCommandService;
    private final CarQueryService carQueryService;

    @PostMapping("/car/api/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponseDTO addCar(@RequestBody CarRequestDTO carRequestDTO) {
        Car car = new Car(carRequestDTO.getBrand(), carRequestDTO.getModel(), carRequestDTO.getChassis());
        Car saved = carCommandService.saveCar(car);
        return convertToResponseDTO(saved);
    }

    @DeleteMapping("/car/api/delete/{id}")
    public void deleteCar(@PathVariable Long id) {
        carCommandService.deleteCar(id);
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