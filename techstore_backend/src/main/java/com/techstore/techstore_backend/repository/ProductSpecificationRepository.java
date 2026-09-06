package com.techstore.techstore_backend.repository;

import com.techstore.techstore_backend.entity.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification,Integer> {
}
