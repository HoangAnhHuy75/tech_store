package com.techstore.techstore_backend.modules.product.controller;

import com.techstore.techstore_backend.modules.product.dto.request.ProductRequest;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import com.techstore.techstore_backend.modules.product.dto.response.ProductResponse;
import com.techstore.techstore_backend.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest request){
        ProductResponse productResponse = productService.createProduct(request);
        return ResponseEntity.ok(ApiResponse.<ProductResponse>builder()
                .code(201)
                .message("Tạo sản phẩm thành công")
                .result(productResponse)
                .build());
    }
}
