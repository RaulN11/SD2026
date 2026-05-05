package cars.microservice.CarsMicroservice.repositories;

import cars.microservice.CarsMicroservice.models.Advertisement;
import cars.microservice.CarsMicroservice.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    public List<Advertisement> findAdvertisementByUserId(Long userId);
    public List<Advertisement> findAdvertisementByCarIn(List<Car> cars);
    public Advertisement findAdvertisementById(Long id);
}
