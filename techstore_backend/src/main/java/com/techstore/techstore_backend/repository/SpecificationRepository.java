package com.techstore.techstore_backend.repository;

import com.techstore.techstore_backend.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface SpecificationRepository extends JpaRepository<Specification, Integer> {
}
