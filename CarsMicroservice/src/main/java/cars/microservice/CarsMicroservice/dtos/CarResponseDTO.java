package cars.microservice.CarsMicroservice.dtos;

import lombok.Data;

@Data
public class CarResponseDTO {
    private Long id;
    private String brand;
    private String model;
    private String chassis;
}
