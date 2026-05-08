package cars.microservice.CarsMicroservice.models;

import cars.microservice.CarsMicroservice.dtos.UserResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component
public class UserClient {
    private final RestClient restClient;
    @Value("${internal.secret}")
    private String internalSecret;

    public UserClient(@Value("${users.microservice.url}") String baseUrl){
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
    public UserResponseDTO getUserByEmail(String email){
        return restClient.get()
                .uri("/users/api/internal/by-email?email={email}", email)
                .header("X-Internal-Secret", internalSecret)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (res, req)->{
                    throw new RuntimeException("User not found for email:" + email);
                })
                .body(UserResponseDTO.class);
    }
    public UserResponseDTO getUserById(Long id){
        return restClient.get()
                .uri("/users/api/internal/{id}", id)
                .header("X-Internal-Secret", internalSecret)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (res, req)->{
                    throw new RuntimeException("User not found for id" + id);
                })
                .body(UserResponseDTO.class);
    }

}
