package com.techstore.techstore_backend.modules.category.dto.response;


import com.techstore.techstore_backend.modules.category.entity.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    Integer id;
    String name;
    Category parent;
}