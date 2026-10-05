package com.techstore.techstore_backend.modules.specification.controller;

import com.techstore.techstore_backend.modules.specification.dto.request.SpecificationRequest;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import com.techstore.techstore_backend.modules.specification.dto.response.SpecificationResponse;
import com.techstore.techstore_backend.modules.specification.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specifications")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<SpecificationResponse>> createSpecification(@RequestBody SpecificationRequest request) {
        SpecificationResponse specificationResponse = specificationService.createSpecification(request);
        return ResponseEntity.ok(ApiResponse.<SpecificationResponse>builder()
                .code(201)
                .message("Thêm thông số kỹ thuật thành công")
                .result(specificationResponse)
                .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SpecificationResponse>>> getAllSpecifications() {
        List<SpecificationResponse> specifications = specificationService.getAllSpecifications();
        return ResponseEntity.ok(ApiResponse.<List<SpecificationResponse>>builder()
                .code(200)
                .message("Lấy danh sách thông số kỹ thuật thành công")
                .result(specifications)
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecificationResponse>> findById(@PathVariable Integer id) {
        SpecificationResponse specificationResponse = specificationService.findById(id);
        return ResponseEntity.ok(ApiResponse.<SpecificationResponse>builder()
                .code(200)
                .message("Tìm thông số kỹ thuật thành công")
                .result(specificationResponse)
                .build()
        );
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<List<SpecificationResponse>>> findByCategories_Id(@PathVariable Integer id) {
        List<SpecificationResponse> specifications = specificationService.findByCategories_Id(id);
        return ResponseEntity.ok(ApiResponse.<List<SpecificationResponse>>builder()
                .code(200)
                .message("Lấy danh sách thông số kỹ thuật theo mã loại sản phẩm thành công")
                .result(specifications)
                .build()
        );
    }
}