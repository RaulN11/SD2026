package com.rauln.CarKet.controllers;

import com.rauln.CarKet.dto.AdRequestDTO;
import com.rauln.CarKet.model.Advertisement;
import com.rauln.CarKet.model.Car;
import com.rauln.CarKet.model.User;
import com.rauln.CarKet.repositories.AdvertisementRepository;
import com.rauln.CarKet.services.AdvertisementService;
import com.rauln.CarKet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/ad/api")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final UserService userService;

    @PostMapping(value = "/publish", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Advertisement publishAd(Principal principal,
                                   @RequestPart("adData") AdRequestDTO requestBody,
                                   @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
       return advertisementService.publishAd(principal.getName(), requestBody, image);

    }
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
