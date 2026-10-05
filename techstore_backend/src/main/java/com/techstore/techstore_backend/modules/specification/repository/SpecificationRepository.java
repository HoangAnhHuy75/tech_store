package com.techstore.techstore_backend.modules.specification.repository;

import com.techstore.techstore_backend.modules.specification.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecificationRepository extends JpaRepository<Specification, Integer> {
}
