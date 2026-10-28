package com.techstore.techstore_backend.modules.rule.controller;

import com.techstore.techstore_backend.modules.rule.dto.request.RuleRequest;
import com.techstore.techstore_backend.modules.rule.dto.response.RuleResponse;
import com.techstore.techstore_backend.modules.rule.service.RuleService;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rules")
@RequiredArgsConstructor
public class RuleController {
    private final RuleService ruleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RuleResponse>> createRule(@Valid @RequestBody RuleRequest request) {
        RuleResponse response = ruleService.createRule(request);
        return ResponseEntity.ok(ApiResponse.<RuleResponse>builder()
                .code(201)
                .message("Rule created successfully")
                .result(response)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RuleResponse>>> findAll() {
        List<RuleResponse> listRule = ruleService.findAll();
        return ResponseEntity.ok(ApiResponse.<List<RuleResponse>>builder()
                .code(200)
                .message("Find all successfully")
                .result(listRule)
                .build());
    }
}
