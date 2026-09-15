package com.techstore.techstore_backend.repository;

import com.techstore.techstore_backend.entity.Category;
import com.techstore.techstore_backend.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);
    List<Category> findByParentIdIsNull();
    List<Category> findByParentId(Integer id);
}
