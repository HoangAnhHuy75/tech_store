package com.techstore.techstore_backend.modules.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecificationRequest {
    @NotNull(message = "Product ID không được null")
    Integer productId;

    @NotNull(message = "Specifcation ID không được null")
    Integer specificationId;

    @NotBlank(message = "Giá trị thông số khong được để trống")
    String value;
}
