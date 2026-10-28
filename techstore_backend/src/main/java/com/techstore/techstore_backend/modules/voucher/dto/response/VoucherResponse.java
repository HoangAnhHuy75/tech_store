package com.techstore.techstore_backend.modules.voucher.dto.response;

import com.techstore.techstore_backend.modules.voucher.enums.VoucherStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherResponse {
    Integer id;
    Integer quantity;
    Integer quantityRemain;
    VoucherStatus status;
}
