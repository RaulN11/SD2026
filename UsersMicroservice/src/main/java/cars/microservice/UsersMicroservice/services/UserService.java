package cars.microservice.UsersMicroservice.services;

import cars.microservice.UsersMicroservice.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    public User loadByEmail(String email);
    public User saveUser(User user);
    public boolean verifyUser(String email, String rawPassword);
}
