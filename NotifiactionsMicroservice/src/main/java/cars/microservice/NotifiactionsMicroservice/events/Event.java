package cars.microservice.NotifiactionsMicroservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Event {
    public String userEmail;
    public String brand;
    public String model;
}