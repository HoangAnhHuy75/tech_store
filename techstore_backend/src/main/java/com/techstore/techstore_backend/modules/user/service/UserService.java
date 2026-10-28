package com.techstore.techstore_backend.modules.user.service;

import com.techstore.techstore_backend.modules.user.dto.request.UserRequest;
import com.techstore.techstore_backend.modules.user.dto.response.UserResponse;
import com.techstore.techstore_backend.modules.role.entity.Role;
import com.techstore.techstore_backend.modules.user.entity.User;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import com.techstore.techstore_backend.modules.role.repository.RoleRepository;
import com.techstore.techstore_backend.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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
                .build();
        if (request.getRoleNames() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoleNames());
            user.setRoles(new HashSet<>(roles));
        }
        return mapToUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Integer id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        user.setName(request.getName());
        user.setPhone(request.getPhone());

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
    public UserResponse findById(Integer id){
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
                .userType(user.getUserType())
                .roles(user.getRoles())
                .build();
    }
}
