package cars.microservice.CarsMicroservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdCreatedEvent extends Event{
    private int price;
    public AdCreatedEvent(String userEmail, String brand, String model, int price){
        super(userEmail, brand, model);
        this.price = price;
    }
}
