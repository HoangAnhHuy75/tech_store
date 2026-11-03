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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final SpecificationRepository specificationRepository;
    private final CategorySpecificationRepository categorySpecificationRepository;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXIST);
        }
        Category parentCategory = null;
        if (request.getParentId() != null) {
            parentCategory = categoryRepository.findById(request.getParentId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));
        }

        Category category = Category.builder()
                .name(request.getName())
                .parent(parentCategory)
                .build();

        Category savedCategory = categoryRepository.save(category);

        if (request.getSpecificationIds() != null && !request.getSpecificationIds().isEmpty()) {
            List<Specification> specifications = specificationRepository.findAllById(request.getSpecificationIds());
            for (Specification specification : specifications) {
                CategorySpecification categorySpecification = new CategorySpecification();
                categorySpecification.setCategory(savedCategory);
                categorySpecification.setSpecification(specification);
                categorySpecificationRepository.save(categorySpecification);
            }
        }

        return mapToCategoryResponse(savedCategory);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));

        category.setName(request.getName());

        if (request.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(request.getParentId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));
            category.setParent(parentCategory);
        }

        if (request.getSpecificationIds() != null) {
            List<CategorySpecification> existingRelations = categorySpecificationRepository.findByCategoryId(id);
            if (!existingRelations.isEmpty()) {
                categorySpecificationRepository.deleteAll(existingRelations);
            }

            if (!request.getSpecificationIds().isEmpty()) {
                List<Specification> specifications = specificationRepository.findAllById(request.getSpecificationIds());
                for (Specification specification : specifications) {
                    CategorySpecification categorySpecification = CategorySpecification.builder()
                            .category(category)
                            .specification(specification)
                            .build();
                    categorySpecificationRepository.save(categorySpecification);
                }
            }
        }

        return mapToCategoryResponse(categoryRepository.save(category));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteCategory(Integer id) {
        categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));
        categorySpecificationRepository.deleteAll(categorySpecificationRepository.findByCategoryId(id));
        categoryRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CategoryResponse findById(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));
        return mapToCategoryResponse(category);
    }

    public List<CategoryResponse> findAll() {
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
