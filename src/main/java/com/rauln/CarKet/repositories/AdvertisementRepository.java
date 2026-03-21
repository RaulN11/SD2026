package com.rauln.CarKet.repositories;

import com.rauln.CarKet.model.Advertisement;
import com.rauln.CarKet.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    public List<Advertisement> findAdvertisementsByUserId(Long userId);
    public List<Advertisement> findAdvertisementsByCarIn(List<Car> cars);
    public Advertisement findAdvertisementById(Long id);
}
