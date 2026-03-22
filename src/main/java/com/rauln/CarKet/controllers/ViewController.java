package com.rauln.CarKet.controllers;

import com.rauln.CarKet.model.Advertisement;
import com.rauln.CarKet.model.User;
import com.rauln.CarKet.services.AdvertisementService;
import com.rauln.CarKet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class    ViewController {
    private final AdvertisementService advertisementService;
    private final UserService userService;

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
    @GetMapping("/addetails/{id}")
    public String adDetailsPage(@PathVariable Long id, Model model) {
        Advertisement ad = advertisementService.loadAdById(id);
        if (ad == null) { throw new RuntimeException("Ad not found"); }
        model.addAttribute("ad", ad);
        return "addetails";
    }
    @GetMapping("/ownads")
    public String ownAds(Authentication authentication, Model model){
        User user = userService.loadByEmail(authentication.getName());
        List<Advertisement> ads = advertisementService.loadAdsByUser(user.getId());
        model.addAttribute("ads", ads);
        return "resultspage";
    }
}
