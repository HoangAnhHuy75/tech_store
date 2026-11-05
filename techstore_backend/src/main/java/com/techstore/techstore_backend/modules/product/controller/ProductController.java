package com.techstore.techstore_backend.modules.product.controller;

import com.techstore.techstore_backend.modules.product.dto.request.ProductRequest;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import com.techstore.techstore_backend.modules.product.dto.response.ProductResponse;
import com.techstore.techstore_backend.modules.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> findAll() {
        List<ProductResponse> products = productService.findAll();
        return ResponseEntity.ok(ApiResponse.<List<ProductResponse>>builder()
                .code(200)
                .message("Lấy danh sách sản phẩm thành công")
                .result(products)
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest request){
        ProductResponse productResponse = productService.createProduct(request);
        return ResponseEntity.ok(ApiResponse.<ProductResponse>builder()
                .code(201)
                .message("Tạo sản phẩm thành công")
                .result(productResponse)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(ApiResponse.<ProductResponse>builder().code(200).message("Cập nhật sản phẩm thành công").result(productService.updateProduct(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().code(200).message("Xóa sản phẩm thành công").build());
    }
}
