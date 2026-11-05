package com.techstore.techstore_backend.modules.product.repository;

import com.techstore.techstore_backend.modules.product.entity.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification,Integer> {
    List<ProductSpecification> findByProductId(Integer productId);
}
