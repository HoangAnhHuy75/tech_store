package com.techstore.techstore_backend.service;

import com.techstore.techstore_backend.dto.request.UserRequest;
import com.techstore.techstore_backend.dto.response.UserResponse;
import com.techstore.techstore_backend.entity.Role;
import com.techstore.techstore_backend.entity.User;
import com.techstore.techstore_backend.exception.AppException;
import com.techstore.techstore_backend.exception.ErrorCode;
import com.techstore.techstore_backend.repository.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXIST);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .dob(request.getDob())
                .build();
        if (request.getRoleNames() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoleNames());
            user.setRoles(new HashSet<>(roles));
        }
        return mapToUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(String id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setDob(request.getDob());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getRoleNames() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoleNames());
            user.setRoles(new HashSet<>(roles));
        }

        return mapToUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
