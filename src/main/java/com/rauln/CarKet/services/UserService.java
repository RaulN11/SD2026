package com.rauln.CarKet.services;

import com.rauln.CarKet.model.User;
import com.rauln.CarKet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public interface UserService extends UserDetailsService {
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException ;
    public User loadByEmail(String email);
    public User saveUser(User user);
}
