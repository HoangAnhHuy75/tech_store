package com.techstore.techstore_backend.service;

import com.techstore.techstore_backend.dto.request.CategoryRequest;
import com.techstore.techstore_backend.dto.response.CategoryResponse;
import com.techstore.techstore_backend.entity.Category;
import com.techstore.techstore_backend.exception.AppException;
import com.techstore.techstore_backend.exception.ErrorCode;
import com.techstore.techstore_backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXIST);
        }
        Category parentCategory = null;
        if (categoryRequest.getParentId() != null) {
            parentCategory = categoryRepository.findById(categoryRequest.getParentId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));
        }
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .parent(parentCategory)
                .build();
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

    private CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getParent())
                .build();
    }
}