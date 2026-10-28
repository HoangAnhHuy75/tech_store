package com.techstore.techstore_backend.modules.product.dto.response;

import com.techstore.techstore_backend.modules.category.entity.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Integer id;
    String name;
    BigDecimal price;
    Integer quantity;
    Boolean isActive;
    Category category;
}
