package com.rauln.CarKet.controllers;

import com.rauln.CarKet.model.Advertisement;
import com.rauln.CarKet.services.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class ViewController {
    private final AdvertisementService advertisementService;

    @GetMapping("/salepage")
    public String salePage(){
        return "carsale";
    }

    @GetMapping("/searchpage")
    public String searchPage(){
        return "carsearch";
    }
    @GetMapping("/resultspage")
    public String resultsPage(@RequestParam(required = false) String brand,
                              @RequestParam(required = false) String model,
                              @RequestParam(required = false) String chassis,
                              Model m1){
        List<Advertisement> foundAds = advertisementService.loadAdsByCar(brand, model, chassis);
        m1.addAttribute("ads", foundAds);
        return "resultspage";
    }
}
