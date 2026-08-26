package com.techstore.techstore_backend.controller;

import com.techstore.techstore_backend.dto.request.AuthenticationRequest;
import com.techstore.techstore_backend.dto.request.IntrospectRequest;
import com.techstore.techstore_backend.dto.response.ApiResponse;
import com.techstore.techstore_backend.dto.response.AuthenticationResponse;
import com.techstore.techstore_backend.dto.response.IntrospectResponse;
import com.techstore.techstore_backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.login(request);
        return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
                .code(201)
                .message("Đăng nhập thành công")
                .result(authenticationResponse)
                .build());

    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request) throws Exception{
        IntrospectResponse introspectResponse = authenticationService.introspect(request);
        return ResponseEntity.ok(ApiResponse.<IntrospectResponse>builder()
                .code(201)
                .message("Verify token thành công !")
                .result(introspectResponse)
                .build());
    }

}
