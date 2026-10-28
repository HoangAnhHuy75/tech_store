package com.techstore.techstore_backend.modules.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest {

    @NotNull(message = "Product ID không được null")
    Integer productId;

    @Min(value = 1,message = "So lượng nhỏ nhất là 1")
    @NotNull(message = "So lượng không được null")
    Integer quantity;
}
