package cars.microservice.CarsMicroservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AdDeletedEvent extends Event{
    public AdDeletedEvent(String userEmail, String brand, String model){
        super(userEmail, brand, model);
    }
}
