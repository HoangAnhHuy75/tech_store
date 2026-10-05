package com.techstore.techstore_backend.modules.product.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    String name;
    Double price;
    Integer quantity;
    Integer categoryId;
    List<ProductSpecificationRequest> productSpecificationRequests;
}
