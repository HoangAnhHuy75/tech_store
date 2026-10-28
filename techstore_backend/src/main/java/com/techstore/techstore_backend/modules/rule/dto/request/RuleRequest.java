package com.techstore.techstore_backend.modules.rule.dto.request;

import com.techstore.techstore_backend.modules.rule.enums.RuleType;
import com.techstore.techstore_backend.modules.user.enums.UserType;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RuleRequest {
    @NotBlank(message = "Tên rule không được để trống")
    @Size(max = 255, message = "Tên rule tối đa 255 ký tự")
    String name;

    @NotNull(message = "CampaignID không được để trống")
    Integer campaignId;

    @NotNull(message = "Loại rule không được để trống")
    RuleType ruleType;

    @NotNull(message = "Loại user không được để trống")
    UserType userType;

    @Min(value = 0, message = "Giá trị (value) phải lớn hơn hoặc bằng 0")
    BigDecimal value;

    @Min(value = 0, message = "Giá trị giảm tối đa phải lớn hơn hoặc bằng 0")
    BigDecimal maxDiscountValue;

    @Min(value = 0, message = "Giá trị đơn hàng tối thiểu phải lớn hơn hoặc bằng 0")
    BigDecimal minOrderValue;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    @FutureOrPresent(message = "Thời gian bắt đầu phải ở hiện tại hoặc tương lai")
    LocalDateTime startTime;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    @Future(message = "Thời gian kết thúc phải ở tương lai")
    LocalDateTime endTime;

    Map<String, Object> payload;
}
