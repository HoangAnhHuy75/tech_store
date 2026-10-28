package com.techstore.techstore_backend.modules.rule.service;

import com.techstore.techstore_backend.modules.order.entity.Order;
import com.techstore.techstore_backend.modules.rule.entity.Rule;
import com.techstore.techstore_backend.modules.rule.enums.RuleType;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class TieredStrategy implements RuleStrategy {
    @Override
    public RuleType getRuleType() {
        return RuleType.TIERED;
    }

    @Override
    public BigDecimal calculateDiscount(Order order, Rule rule) {
        List<Map<String, Object>> tiers = (List<Map<String, Object>>) rule.getPayload().get("tiers");
        if (tiers == null || tiers.isEmpty()) {
            throw new AppException(ErrorCode.RULE_MISS_PAYLOAD);
        }
        return tiers.stream()
                .filter(tier -> {
                    BigDecimal minOrderValue = new BigDecimal(tier.get("min_order_value").toString());
                    return order.getTotalPrice().compareTo(minOrderValue) >= 0;
                })
                .map(tier -> new BigDecimal(tier.get("discount_value").toString()))
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }
}
