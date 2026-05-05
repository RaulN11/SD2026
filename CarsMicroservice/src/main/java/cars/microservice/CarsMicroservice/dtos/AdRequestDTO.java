package cars.microservice.CarsMicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class AdRequestDTO {
    private String brand;
    private String model;
    private String chassis;
    private Integer year;
    private Integer price;
    private Long userId;
}