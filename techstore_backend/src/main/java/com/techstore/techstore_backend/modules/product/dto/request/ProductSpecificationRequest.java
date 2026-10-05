package com.techstore.techstore_backend.modules.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecificationRequest {
    Integer productId;
    Integer specificationId;
    String value;
}
