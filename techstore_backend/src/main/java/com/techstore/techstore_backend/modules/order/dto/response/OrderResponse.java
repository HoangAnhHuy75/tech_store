package com.techstore.techstore_backend.modules.order.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Integer id;
    BigDecimal totalPrice;
    BigDecimal discount;
    BigDecimal finalPrice;
}
