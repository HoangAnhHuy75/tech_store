package com.techstore.techstore_backend.modules.rule.dto.response;

import com.techstore.techstore_backend.modules.campaign.entity.Campaign;
import com.techstore.techstore_backend.modules.rule.enums.RuleStatus;
import com.techstore.techstore_backend.modules.rule.enums.RuleType;
import com.techstore.techstore_backend.modules.user.enums.UserType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RuleResponse {
    Integer id;
    String name;
    Campaign campaign;
    RuleType ruleType;
    BigDecimal value;
    BigDecimal maxDiscountValue;
    BigDecimal minOrderValue;
    Map<String, Object> payload;
    LocalDateTime startTime;
    LocalDateTime endTime;
    RuleStatus status;
    UserType userType;
    Boolean isDelete;
    LocalDateTime createAt;
}
