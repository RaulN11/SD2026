package cars.microservice.CarsMicroservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CarRequestDTO {
    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Chassis is required")
    private String chassis;
}
