package cars.microservice.NotifiactionsMicroservice.events;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AdDeletedEvent extends Event {
    public AdDeletedEvent(String userEmail, String brand, String model) {
        super(userEmail, brand, model);
    }
}