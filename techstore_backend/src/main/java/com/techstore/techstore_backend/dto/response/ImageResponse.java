package com.techstore.techstore_backend.dto.response;

import com.techstore.techstore_backend.entity.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageResponse {
    Integer id;
    String urlImage;
    Product product;
}
