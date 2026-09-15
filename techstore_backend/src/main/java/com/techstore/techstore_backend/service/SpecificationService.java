package com.techstore.techstore_backend.service;

import com.techstore.techstore_backend.dto.request.SpecificationRequest;
import com.techstore.techstore_backend.dto.response.SpecificationResponse;
import com.techstore.techstore_backend.entity.Specification;
import com.techstore.techstore_backend.repository.CategorySpecificationRepository;
import com.techstore.techstore_backend.repository.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecificationRepository specificationRepository;

    @Autowired
    private CategorySpecificationRepository categorySpecificationRepository;

    public SpecificationResponse createSpecification(SpecificationRequest request) {
        Specification specification = Specification.builder()
                .name(request.getName())
                .unit(request.getUnit())
                .build();
        return mapToSpecificationResponse(specificationRepository.save(specification));
    }

    public List<SpecificationResponse> getAllSpecifications() {
        List<Specification> specifications = specificationRepository.findAll();
        return specifications.stream()
                .map(this::mapToSpecificationResponse)
                .toList();
    }

    public SpecificationResponse findById(Integer id) {
        Specification specification =  specificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy thông số kỹ thuật"));
        return mapToSpecificationResponse(specification);
    }

    public List<SpecificationResponse> findByCategories_Id(Integer id){
        List<Specification> specifications = categorySpecificationRepository.findSpecificationsByCategoryId(id);
        return specifications.stream()
                .map(this::mapToSpecificationResponse)
                .toList();
    }

    public SpecificationResponse mapToSpecificationResponse(Specification specification) {
        return SpecificationResponse.builder()
                .id(specification.getId())
                .name(specification.getName())
                .unit(specification.getUnit())
                .build();
    }
}