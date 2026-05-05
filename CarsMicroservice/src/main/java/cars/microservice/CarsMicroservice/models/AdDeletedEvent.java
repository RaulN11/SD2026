package cars.microservice.CarsMicroservice.models;

public class AdDeletedEvent extends Event{
    public AdDeletedEvent(String userEmail, String brand, String model){
        super(userEmail, brand, model);
    }
}
