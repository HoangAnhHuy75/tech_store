package com.techstore.techstore_backend.modules.user.dto.response;

import com.techstore.techstore_backend.modules.user.entity.User;
import com.techstore.techstore_backend.modules.user.enums.UserVoucherStatus;
import com.techstore.techstore_backend.modules.voucher.entity.Voucher;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserVoucherResponse {
    Integer id;
    Voucher voucher;
    LocalDateTime collectedAt;
    UserVoucherStatus status;
}
