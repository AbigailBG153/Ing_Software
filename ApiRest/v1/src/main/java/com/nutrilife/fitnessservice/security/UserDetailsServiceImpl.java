package com.nutrilife.fitnessservice.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.nutrilife.fitnessservice.model.entity.User;
import com.nutrilife.fitnessservice.repository.UserRespository;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRespository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws
    UsernameNotFoundException {
        User user = userRepository
        .findByEmail(username)
        .orElseThrow(() -> new
    UsernameNotFoundException(username));
        return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .roles(user.getRole().name())
        .build();
    }
}