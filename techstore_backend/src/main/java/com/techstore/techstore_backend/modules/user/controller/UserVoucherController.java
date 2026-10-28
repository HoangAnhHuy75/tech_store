package com.techstore.techstore_backend.modules.user.controller;

import com.techstore.techstore_backend.modules.user.dto.request.UserVoucherRequest;
import com.techstore.techstore_backend.modules.user.dto.response.UserVoucherResponse;
import com.techstore.techstore_backend.modules.user.service.UserVoucherService;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-vouchers")
@RequiredArgsConstructor
public class UserVoucherController {
    private final UserVoucherService userVoucherService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserVoucherResponse>> create(
            @Valid @RequestBody UserVoucherRequest request) {
        UserVoucherResponse response = userVoucherService.create(request);
        return ResponseEntity.ok(
                ApiResponse.<UserVoucherResponse>builder()
                        .code(201)
                        .message("Create user_voucher success")
                        .result(response)
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserVoucherResponse>>> findAll(
            @Valid @RequestBody UserVoucherRequest request) {
        List<UserVoucherResponse> response = userVoucherService.findAll();
        return ResponseEntity.ok(
                ApiResponse.<List<UserVoucherResponse>>builder()
                        .code(200)
                        .message("Find all user_voucher success")
                        .result(response)
                        .build());
    }
}
