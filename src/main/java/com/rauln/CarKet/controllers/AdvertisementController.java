package com.rauln.CarKet.controllers;

import com.rauln.CarKet.model.Advertisement;
import com.rauln.CarKet.model.Car;
import com.rauln.CarKet.services.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/ad/api")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @PostMapping("/publish")
    public Advertisement publishAd(Principal principal)
    @GetMapping("/findall")
    public List<Advertisement> findAllAds(){
        return advertisementService.loadAllAds();
    }
    @GetMapping("/findby/{id}")
    public List<Advertisement> findByUser(@PathVariable Long id){
        return advertisementService.loadAdsByUser(id);
    }
    @GetMapping("/search")
    public List<Advertisement> findByCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String chassis
    ){
        return advertisementService.loadAdsByCar(brand, model, chassis);
    }
}
