package com.techstore.techstore_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationRequest {
    @NotBlank(message = "Tên thông số kỹ thuật không đc để trống")
    String name;
    @NotBlank(message = "Đơn vị thông số kỹ thuật không đc để trống")
    String unit;
}
