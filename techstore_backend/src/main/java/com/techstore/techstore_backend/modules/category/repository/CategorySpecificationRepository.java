package com.techstore.techstore_backend.modules.category.repository;

import com.techstore.techstore_backend.modules.category.entity.CategorySpecification;
import com.techstore.techstore_backend.modules.specification.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorySpecificationRepository extends JpaRepository<CategorySpecification, Integer> {
    @Query("""
            SELECT cs.specification
            FROM CategorySpecification cs
            WHERE cs.category.id = :categoryId
            """)
    List<Specification> findSpecificationsByCategoryId(@Param("categoryId") Integer categoryId);
}
