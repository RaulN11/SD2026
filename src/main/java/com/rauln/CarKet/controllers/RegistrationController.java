package com.rauln.CarKet.controllers;

import com.rauln.CarKet.model.User;
import com.rauln.CarKet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showRegistrationForm(){
        return "register";
    }
    @PostMapping
    public String createUser(@ModelAttribute User user, Model model){
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error","Email address is used");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_CLIENT");
        userRepository.save(user);
        return "redirect:/login";

    }
}
