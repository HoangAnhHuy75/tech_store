package com.techstore.techstore_backend.repository;

import com.techstore.techstore_backend.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SpecificationRepository extends JpaRepository<Specification, Integer> {
    public List<Specification> findByCategories_Id(Integer id);
}
