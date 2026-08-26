package com.techstore.techstore_backend.config;

import com.techstore.techstore_backend.entity.User;
import com.techstore.techstore_backend.enums.Role;
import com.techstore.techstore_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ApplicationInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Bean
    public ApplicationRunner applicationRunner(){
        return args ->{
            if (!userRepository.existsByUsername("admin")) {
                Set<String> roles = new HashSet<>();
                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123456"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
