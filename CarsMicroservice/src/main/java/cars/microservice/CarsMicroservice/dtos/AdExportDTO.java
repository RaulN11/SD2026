package cars.microservice.CarsMicroservice.dtos;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class AdExportDTO {
    private Long id;
    private String brand;
    private String model;
    private String chassis;
    private Integer year;
    private Integer price;
    private String seller;
}
