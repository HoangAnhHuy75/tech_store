package com.techstore.techstore_backend.modules.campaign.dto.response;

import com.techstore.techstore_backend.modules.campaign.enums.CampaignStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampaignResponse {
    Integer id;
    String name;
    CampaignStatus status;
    BigDecimal budget;
    LocalDateTime startTime;
    LocalDateTime endTime;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    boolean isDelete;
}
