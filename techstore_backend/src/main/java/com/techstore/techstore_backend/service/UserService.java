package com.techstore.techstore_backend.service;

import com.techstore.techstore_backend.dto.request.UserRequest;
import com.techstore.techstore_backend.dto.response.UserResponse;
import com.techstore.techstore_backend.entity.User;
import com.techstore.techstore_backend.enums.Role;
import com.techstore.techstore_backend.exception.AppException;
import com.techstore.techstore_backend.exception.ErrorCode;
import com.techstore.techstore_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public UserResponse createUser(UserRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXIST);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        Set<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .dob(request.getDob())
                .roles(roles)
                .build();
        return mapToUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        List<User> listUser = userRepository.findAll();
        return listUser.stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse findById(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        return mapToUserResponse(user);
    }

    public UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .phone(user.getPhone())
                .dob(user.getDob())
                .roles(user.getRoles())
                .build();
    }
}
