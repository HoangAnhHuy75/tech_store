package com.techstore.techstore_backend.modules.permission.controller;

import com.techstore.techstore_backend.modules.permission.dto.request.PermissionRequest;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import com.techstore.techstore_backend.modules.permission.dto.response.PermissionResponse;
import com.techstore.techstore_backend.modules.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(@RequestBody PermissionRequest request) {
        PermissionResponse permissionResponse = permissionService.createPermission(request);
        return ResponseEntity.ok(ApiResponse.<PermissionResponse>builder()
                .code(201)
                .message("Permission created successfully")
                .result(permissionResponse)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllUsers() {
        List<PermissionResponse> permissionResponses = permissionService.findAll();
        return ResponseEntity.ok(ApiResponse.<List<PermissionResponse>>builder()
                .code(201)
                .message("Get roster permissions successfully")
                .result(permissionResponses)
                .build());
    }
}
