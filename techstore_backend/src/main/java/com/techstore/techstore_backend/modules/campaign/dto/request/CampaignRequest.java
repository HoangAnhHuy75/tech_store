package com.techstore.techstore_backend.modules.campaign.dto.request;


import com.techstore.techstore_backend.modules.campaign.enums.CampaignStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampaignRequest {

    @NotBlank(message = "Tên chiến dịch không được để trống")
    @Size(min = 5, max = 100, message = "Tên chiến dịch phải ít nhất 5 ký tự và không quá 100 ký tự")
    String name;

    @NotNull(message = "Ngân sách không được để trống")
    @Positive(message = "Ngân sách phải > 0")
    BigDecimal budget;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    LocalDateTime startTime;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    LocalDateTime endTime;
}
