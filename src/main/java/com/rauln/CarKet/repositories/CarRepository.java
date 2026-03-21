package com.rauln.CarKet.repositories;

import com.rauln.CarKet.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE "+
            "(:brand IS NULL OR :brand = '' OR LOWER(c.brand) = LOWER(:brand)) AND " +
            "(:model IS NULL OR :model = '' OR LOWER(c.model) = LOWER(:model)) AND " +
            "(:chassis IS NULL OR :chassis = '' OR LOWER(c.chassis) = LOWER(:chassis))"
    )
    List<Car>searchCars(@Param("brand") String brand,
                        @Param("model") String model,
                        @Param("chassis") String chassis
                        );


}
