package com.techstore.techstore_backend.shared.config;

import com.techstore.techstore_backend.modules.role.entity.Role;
import com.techstore.techstore_backend.modules.user.entity.User;
import com.techstore.techstore_backend.modules.user.repository.UserRepository;
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
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123456"))
                        .build();
                userRepository.save(user);
            }
        };
    }
}
