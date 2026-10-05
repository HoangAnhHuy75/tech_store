package com.techstore.techstore_backend.modules.product.repository;

import com.techstore.techstore_backend.modules.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
