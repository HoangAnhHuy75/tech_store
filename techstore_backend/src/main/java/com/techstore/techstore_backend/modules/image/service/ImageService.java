package com.techstore.techstore_backend.modules.image.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techstore.techstore_backend.modules.image.dto.response.ImageResponse;
import com.techstore.techstore_backend.modules.image.entity.Image;
import com.techstore.techstore_backend.modules.product.entity.Product;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import com.techstore.techstore_backend.modules.image.repository.ImageRepository;
import com.techstore.techstore_backend.modules.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    ProductRepository productRepository;
    public List<ImageResponse> createImages(MultipartFile[] files, Integer productId) throws Exception{
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST));
        List<ImageResponse> imageResponses = new ArrayList<>();
        for(MultipartFile file : files) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "tech_store"));
            String urlImage = uploadResult.get("secure_url").toString();
            Image image = Image.builder()
                    .urlImage(urlImage)
                    .product(product)
                    .build();
            imageResponses.add(mapToImageResponse(imageRepository.save(image)));
        }
        return imageResponses;
    }

    public ImageResponse mapToImageResponse(Image image){
        return ImageResponse.builder()
                .id(image.getId())
                .urlImage(image.getUrlImage())
                .product(image.getProduct())
                .build();
    }
}
