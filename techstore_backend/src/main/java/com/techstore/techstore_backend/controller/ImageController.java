package com.techstore.techstore_backend.controller;

import com.techstore.techstore_backend.dto.response.ApiResponse;
import com.techstore.techstore_backend.dto.response.ImageResponse;
import com.techstore.techstore_backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<ImageResponse>>> createImages(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("productId") Integer productId) throws Exception {
        List<ImageResponse> imageResponses = imageService.createImages(files, productId);
        return ResponseEntity.ok(ApiResponse.<List<ImageResponse>>builder()
                .code(201)
                .message("Thêm ảnh cho sản phẩm thành công")
                .result(imageResponses)
                .build());
    }
}
