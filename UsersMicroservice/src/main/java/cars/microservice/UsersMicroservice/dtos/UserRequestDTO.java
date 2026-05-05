package cars.microservice.UsersMicroservice.dtos;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
