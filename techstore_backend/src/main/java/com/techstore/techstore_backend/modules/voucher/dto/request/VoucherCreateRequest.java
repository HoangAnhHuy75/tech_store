package com.techstore.techstore_backend.modules.voucher.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class VoucherCreateRequest {

    @NotNull(message = "Rule ID khong được để trống")
    Integer ruleId;

    @Min(value = 1,message = "Số lượng voucher lớn hơn hoặc bằng 1")
    @NotNull(message = "Số lượng voucher không được null")
    Integer quantity;

    @Min(value = 1,message = "Giới hạn sử dụng voucher phải lớn hơn hoặc bằng 1")
    @NotNull(message = "Giới hạn số lần sử dụng voucher của user không được null")
    Integer limitClient;
}
