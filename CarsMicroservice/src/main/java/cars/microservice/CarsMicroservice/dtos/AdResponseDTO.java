package cars.microservice.CarsMicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdResponseDTO {
    private Long id;
    private Long userId;
    private CarResponseDTO car;
    private Integer price;
    private Integer year;
    private List<String> images;
}
