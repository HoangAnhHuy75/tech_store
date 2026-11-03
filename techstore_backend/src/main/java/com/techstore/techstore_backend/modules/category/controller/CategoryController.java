package com.techstore.techstore_backend.modules.category.controller;

import com.techstore.techstore_backend.modules.category.dto.request.CategoryRequest;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import com.techstore.techstore_backend.modules.category.dto.response.CategoryResponse;
import com.techstore.techstore_backend.modules.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.createCategory(request);
        return ResponseEntity.ok(ApiResponse.<CategoryResponse>builder()
                .code(201)
                .message("Thêm loại sản phâm thành công")
                .result(categoryResponse)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.findAll();
        return ResponseEntity.ok(ApiResponse.<List<CategoryResponse>>builder()
                .code(200)
                .message("Lấy danh sách loại sản phẩm thành công")
                .result(categories)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> findById(@PathVariable Integer id) {
        CategoryResponse category = categoryService.findById(id);
        return ResponseEntity.ok(ApiResponse.<CategoryResponse>builder()
                .code(200)
                .message("Lấy loại sản phẩm thành công")
                .result(category)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable Integer id, @RequestBody CategoryRequest request) {
        CategoryResponse category = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(ApiResponse.<CategoryResponse>builder()
                .code(200)
                .message("Cập nhật loại sản phẩm thành công")
                .result(category)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().code(200).message("Xóa loại sản phẩm thành công").build());
    }

    @GetMapping("/parents")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> findByParentIdIsNull() {
        List<CategoryResponse> categories = categoryService.findByParentIdIsNull();
        return ResponseEntity.ok(ApiResponse.<List<CategoryResponse>>builder()
                .code(200)
                .message("Lấy danh sách loại sản phẩm cha thành công")
                .result(categories)
                .build());
    }

    @GetMapping("/parents/{id}")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> findByParentId(@PathVariable Integer id) {
        List<CategoryResponse> categories = categoryService.findByParentId(id);
        return ResponseEntity.ok(ApiResponse.<List<CategoryResponse>>builder()
                .code(200)
                .message("Lấy danh sách loại sản phẩm có parentId thành công")
                .result(categories)
                .build());
    }
}
