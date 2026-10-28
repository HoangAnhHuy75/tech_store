package com.techstore.techstore_backend.modules.rule.entity;

import com.techstore.techstore_backend.modules.campaign.entity.Campaign;
import com.techstore.techstore_backend.modules.rule.enums.RuleStatus;
import com.techstore.techstore_backend.modules.rule.enums.RuleType;
import com.techstore.techstore_backend.modules.user.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@Table(name = "rules")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", nullable = false)
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    Campaign campaign;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", nullable = false)
    RuleType ruleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    UserType userType;

    @Column(name = "value", precision = 15, scale = 2)
    BigDecimal value;

    @Column(name = "max_discount_value", precision = 15, scale = 2)
    BigDecimal maxDiscountValue;

    @Column(name = "min_order_value", precision = 15, scale = 2)
    BigDecimal minOrderValue;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", columnDefinition = "jsonb")
    Map<String, Object> payload;

    @Column(name = "start_time", nullable = false)
    LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    LocalDateTime endTime;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    RuleStatus status = RuleStatus.ACTIVE;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;
}
