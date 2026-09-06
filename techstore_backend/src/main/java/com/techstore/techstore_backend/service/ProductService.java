package com.techstore.techstore_backend.service;


import com.techstore.techstore_backend.dto.request.ProductRequest;
import com.techstore.techstore_backend.dto.request.ProductSpecificationRequest;
import com.techstore.techstore_backend.dto.response.ProductResponse;
import com.techstore.techstore_backend.entity.Category;
import com.techstore.techstore_backend.entity.Product;
import com.techstore.techstore_backend.entity.ProductSpecification;
import com.techstore.techstore_backend.entity.Specification;
import com.techstore.techstore_backend.exception.AppException;
import com.techstore.techstore_backend.exception.ErrorCode;
import com.techstore.techstore_backend.repository.CategoryRepository;
import com.techstore.techstore_backend.repository.ProductRepository;
import com.techstore.techstore_backend.repository.ProductSpecificationRepository;
import com.techstore.techstore_backend.repository.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SpecificationRepository specificationRepository;

    @Autowired
    ProductSpecificationRepository productSpecificationRepository;
    public ProductResponse createProduct(ProductRequest request){
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .isActive(true)
                .category(category)
                .build();
        Product savedProduct = productRepository.save(product);

        if (request.getSpecifications() != null) {
            List<ProductSpecification> productSpecifications = request.getSpecifications().stream()
                    .map(item -> {
                        Specification specification = specificationRepository.findById(item.getSpecificationId()).orElseThrow(() -> new AppException(ErrorCode.SPECIFICATION_NOT_EXIST));
                        ProductSpecification productSpecification = new ProductSpecification();
                        productSpecification.setSpecification(specification);
                        productSpecification.setProduct(savedProduct);
                        productSpecification.setValue(item.getValue());
                        return productSpecification;
                    })
                    .toList();
            productSpecificationRepository.saveAll(productSpecifications);
        }
        return mapToProductResponse(savedProduct);
    }

    public ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .isActive(product.getIsActive())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .build();
    }
}
