package com.techstore.techstore_backend.modules.role.controller;

import com.techstore.techstore_backend.modules.role.dto.request.RoleRequest;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import com.techstore.techstore_backend.modules.role.dto.response.RoleResponse;
import com.techstore.techstore_backend.modules.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody RoleRequest request) {
        RoleResponse roleResponse = roleService.createRole(request);
        return ResponseEntity.ok(ApiResponse.<RoleResponse>builder()
                .code(201)
                .message("Role created succesfully")
                .result(roleResponse)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> findAll() {
        List<RoleResponse> roleResponses = roleService.findAll();
        return ResponseEntity.ok(ApiResponse.<List<RoleResponse>>builder()
                .code(201)
                .message("Find all roles succesfully")
                .result(roleResponses)
                .build());
    }
}
