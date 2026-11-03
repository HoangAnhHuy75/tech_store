package com.techstore.techstore_backend.modules.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {
    @NotBlank(message = "Tên loại sản phẩm không được để trống")
    String name;

    Integer parentId;
    Set<Integer> specificationIds;
}
