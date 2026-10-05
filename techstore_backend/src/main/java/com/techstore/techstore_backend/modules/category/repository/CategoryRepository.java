package com.techstore.techstore_backend.modules.category.repository;

import com.techstore.techstore_backend.modules.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);
    List<Category> findByParentIdIsNull();
    List<Category> findByParentId(Integer id);
}
