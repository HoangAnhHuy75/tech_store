package com.techstore.techstore_backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecificationResponse {
    Integer id;
    String name;
    String unit;
}
