package com.techstore.techstore_backend.controller;

import com.techstore.techstore_backend.dto.request.UserRequest;
import com.techstore.techstore_backend.dto.response.ApiResponse;
import com.techstore.techstore_backend.dto.response.UserResponse;
import com.techstore.techstore_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserRequest request) {
        UserResponse userResponse = userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(201)
                .message("Thêm người dùng thành công")
                .result(userResponse)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username {}:",authentication.getName());
        List<UserResponse> listUser = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder()
                .code(201)
                .message("Lấy danh sách user thành công")
                .result(listUser)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable String id) {
        UserResponse userReponse = userService.findById(id);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(201)
                .message("Tìm kiếm theo ID thành công")
                .result(userReponse)
                .build());
    }
}
