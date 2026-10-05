package com.techstore.techstore_backend.modules.category.controller;

import com.techstore.techstore_backend.modules.category.dto.request.CategoryRequest;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import com.techstore.techstore_backend.modules.category.dto.response.CategoryResponse;
import com.techstore.techstore_backend.modules.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

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

    @GetMapping("/parents/{id}")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> findByParentId(@PathVariable Integer id) {
        List<CategoryResponse> categories = categoryService.findByParentId(id);
        return ResponseEntity.ok(ApiResponse.<List<CategoryResponse>>builder()
                .code(201)
                .message("Lấy danh sách loại sản phẩm có parentId thành công")
                .result(categories)
                .build());
    }
}
