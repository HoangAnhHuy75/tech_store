package com.techstore.techstore_backend.modules.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserVoucherRequest {
    @NotNull(message = "Voucher ID không được null")
    Integer voucherId;
}
