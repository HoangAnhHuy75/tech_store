package com.techstore.techstore_backend.repository;

import com.techstore.techstore_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
