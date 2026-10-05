package com.techstore.techstore_backend.modules.category.service;

import com.techstore.techstore_backend.modules.category.dto.request.CategoryRequest;
import com.techstore.techstore_backend.modules.category.dto.response.CategoryResponse;
import com.techstore.techstore_backend.modules.category.entity.Category;
import com.techstore.techstore_backend.modules.category.entity.CategorySpecification;
import com.techstore.techstore_backend.modules.specification.entity.Specification;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import com.techstore.techstore_backend.modules.category.repository.CategoryRepository;
import com.techstore.techstore_backend.modules.category.repository.CategorySpecificationRepository;
import com.techstore.techstore_backend.modules.specification.repository.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SpecificationRepository specificationRepository;

    @Autowired
    CategorySpecificationRepository categorySpecificationRepository;

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

        // Lưu Category
        Category savedCategory = categoryRepository.save(category);

        // Lấy các Specification được chọn
        if (categoryRequest.getSpecificationIds() != null && !categoryRequest.getSpecificationIds().isEmpty()) {
            List<Specification> specifications = specificationRepository.findAllById(categoryRequest.getSpecificationIds());

            // Tạo quan hệ Category - Specification
            for (Specification specification : specifications) {
                CategorySpecification categorySpecification = new CategorySpecification();
                categorySpecification.setCategory(savedCategory);
                categorySpecification.setSpecification(specification);
                categorySpecificationRepository.save(categorySpecification);
            }
        }

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