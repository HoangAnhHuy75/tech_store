package com.techstore.techstore_backend.modules.image.controller;

import com.techstore.techstore_backend.shared.response.ApiResponse;
import com.techstore.techstore_backend.modules.image.dto.response.ImageResponse;
import com.techstore.techstore_backend.modules.image.service.ImageService;
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
    private final ImageService imageService;

    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

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
