package com.techstore.techstore_backend.modules.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    String name;

    @NotNull(message = "Category ID không được null")
    Integer categoryId;

    @NotNull(message = "Giá sản phẩm không được null")
    BigDecimal price;

    @NotNull(message = "Số lượng sản phẩm không được null")
    Integer quantity;

    @NotEmpty(message = "Phải có ít nhất một thông số kỹ thuật")
    List<ProductSpecificationRequest> productSpecificationRequests = new ArrayList<>();
}
