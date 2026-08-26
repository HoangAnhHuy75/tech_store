package com.techstore.techstore_backend.controller;

import com.techstore.techstore_backend.dto.request.CategoryRequest;
import com.techstore.techstore_backend.dto.response.ApiResponse;
import com.techstore.techstore_backend.dto.response.CategoryResponse;
import com.techstore.techstore_backend.entity.Category;
import com.techstore.techstore_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest request){
        CategoryResponse categoryResponse = categoryService.createCategory(request);
        return ResponseEntity.ok(ApiResponse.<CategoryResponse>builder()
                        .code(201)
                        .message("Thêm loại sản phâm thành công")
                        .result(categoryResponse)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.<List<CategoryResponse>>builder()
                .code(201)
                .message("Lấy danh sách loại sản phâm thành công")
                .result(categories)
                .build());
    }

    @GetMapping("/parents")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> findByParentIdIsNull(){
        List<CategoryResponse> categories = categoryService.findByParentIdIsNull();
        return ResponseEntity.ok(ApiResponse.<List<CategoryResponse>>builder()
                .code(201)
                .message("Lấy danh sách loại sản phẩm có parentId null thành công")
                .result(categories)
                .build());
    }
}
