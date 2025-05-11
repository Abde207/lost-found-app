package org.example.lostandfound.service;

import org.example.lostandfound.repository.UserEntity;
import org.example.lostandfound.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InputType inputType = InputType.inputType(username);
        if (InputType.INVALID.equals(inputType))
            throw new IllegalStateException("invalid username or email");


        return (InputType.USERNAME.equals(inputType)
                ? userRepository.findByUsername(username)
                : userRepository.findByEmail(username))
                .map(CustomUserDetailsService::toUser)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private static User toUser(UserEntity user) {
        return new User(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }


}
