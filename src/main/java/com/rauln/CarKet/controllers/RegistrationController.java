package com.rauln.CarKet.controllers;

import com.rauln.CarKet.model.User;
import com.rauln.CarKet.repositories.UserRepository;
import com.rauln.CarKet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String showRegistrationForm(){
        return "register";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user, Model model) {
        try{
            userService.loadByEmail(user.getEmail());
            model.addAttribute("error", "Email is already used");
            return "register";

        }catch (UsernameNotFoundException ex){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("ROLE_" + user.getRole().toUpperCase());
            userService.saveUser(user);
            return "redirect:/login";
        }
    }
}
