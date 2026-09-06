package com.techstore.techstore_backend.service;

import com.techstore.techstore_backend.dto.request.CategoryRequest;
import com.techstore.techstore_backend.dto.response.CategoryResponse;
import com.techstore.techstore_backend.entity.Category;
import com.techstore.techstore_backend.entity.Specification;
import com.techstore.techstore_backend.exception.AppException;
import com.techstore.techstore_backend.exception.ErrorCode;
import com.techstore.techstore_backend.repository.CategoryRepository;
import com.techstore.techstore_backend.repository.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SpecificationRepository specificationRepository;

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        // Kiểm tra category đã tồn tại
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXIST);
        }

        // Tìm category cha
        Category parentCategory = null;

        if (categoryRequest.getParentId() != null) {
            parentCategory = categoryRepository.findById(categoryRequest.getParentId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));
        }

        // Tạo Category
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .parent(parentCategory)
                .build();

        // Lấy Specification
        if (categoryRequest.getSpecificationIds() != null && !categoryRequest.getSpecificationIds().isEmpty()) {
            List<Specification> specifications = specificationRepository.findAllById(categoryRequest.getSpecificationIds());
            category.setSpecifications(new HashSet<>(specifications));
        }

        // Lưu Category + quan hệ ManyToMany
        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryResponse(savedCategory);
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::mapToCategoryResponse)
                .toList();
    }

    public List<CategoryResponse> findByParentIdIsNull() {
        List<Category> categories = categoryRepository.findByParentIdIsNull();
        return categories.stream().
                map(this::mapToCategoryResponse)
                .toList();
    }

    public List<CategoryResponse> findByParentId(Integer id) {
        List<Category> categories = categoryRepository.findByParentId(id);
        return categories.stream()
                .map(this::mapToCategoryResponse)
                .toList();
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getParent())
                .build();
    }
}