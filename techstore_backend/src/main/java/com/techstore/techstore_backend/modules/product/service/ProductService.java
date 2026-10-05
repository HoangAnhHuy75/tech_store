package com.techstore.techstore_backend.modules.product.service;


import com.techstore.techstore_backend.modules.product.dto.request.ProductRequest;
import com.techstore.techstore_backend.modules.product.dto.request.ProductSpecificationRequest;
import com.techstore.techstore_backend.modules.product.dto.response.ProductResponse;
import com.techstore.techstore_backend.modules.category.entity.Category;
import com.techstore.techstore_backend.modules.product.entity.Product;
import com.techstore.techstore_backend.modules.product.entity.ProductSpecification;
import com.techstore.techstore_backend.modules.specification.entity.Specification;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import com.techstore.techstore_backend.modules.category.repository.CategoryRepository;
import com.techstore.techstore_backend.modules.product.repository.ProductRepository;
import com.techstore.techstore_backend.modules.product.repository.ProductSpecificationRepository;
import com.techstore.techstore_backend.modules.specification.repository.SpecificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SpecificationRepository specificationRepository;
    private final ProductSpecificationRepository productSpecificationRepository;

    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));
        Product product = productRepository.save(Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .isActive(true)
                .category(category)
                .build());
        createProductSpecification(product, request.getProductSpecificationRequests());
        return mapToProductResponse(product);
    }

    public void createProductSpecification(Product product, List<ProductSpecificationRequest> productSpecificationRequests) {
        for (ProductSpecificationRequest request : productSpecificationRequests) {
            Specification specification = specificationRepository.findById(request.getSpecificationId()).orElseThrow(() -> new AppException(ErrorCode.SPECIFICATION_NOT_EXIST));
            ProductSpecification productSpecification = ProductSpecification.builder()
                    .product(product)
                    .specification(specification)
                    .value(request.getValue())
                    .build();
            productSpecificationRepository.save(productSpecification);
        }
    }

    public List<ProductResponse> findAll() {
        List<Product> listProduct = productRepository.findAll();
        return listProduct
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    public ProductResponse findById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST));
        return mapToProductResponse(product);
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
