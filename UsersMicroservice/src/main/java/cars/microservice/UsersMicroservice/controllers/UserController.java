package cars.microservice.UsersMicroservice.controllers;

import cars.microservice.UsersMicroservice.dtos.UserRequestDTO;
import cars.microservice.UsersMicroservice.dtos.UserResponseDTO;
import cars.microservice.UsersMicroservice.models.User;
import cars.microservice.UsersMicroservice.services.JwtService;
import cars.microservice.UsersMicroservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO register(@RequestBody UserRequestDTO request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()) );
        user.setRole("ROLE_" + request.getRole().toUpperCase());

        User savedUser = userService.saveUser(user);
        return convertToResponseDTO(savedUser);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDTO loginRequest) {
       User user = userService.loadByEmail(loginRequest.getEmail());
       if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
       }
       return ResponseEntity.ok(jwtService.generateToken(user));
    }

    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
    @GetMapping("/internal/by-email")
    public UserResponseDTO getByEmail(@RequestParam String email,
                                      @RequestHeader("X-Internal-Secret") String secret,
                                      @Value("${internal.secret}") String expectedSecret) {
        if (!secret.equals(expectedSecret)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return convertToResponseDTO(userService.loadByEmail(email));
    }
    @GetMapping("/internal/{id}")
    public UserResponseDTO getById(@PathVariable Long id, @RequestHeader("X-Internal-Secret") String secret, @Value("${internal.secret}") String expectedSecret) {
        if (!secret.equals(expectedSecret)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return convertToResponseDTO(userService.loadById(id));
    }
}
