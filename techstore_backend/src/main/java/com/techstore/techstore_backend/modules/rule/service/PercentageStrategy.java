package com.techstore.techstore_backend.modules.rule.service;

import com.techstore.techstore_backend.modules.order.entity.Order;
import com.techstore.techstore_backend.modules.rule.entity.Rule;
import com.techstore.techstore_backend.modules.rule.enums.RuleType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PercentageStrategy implements RuleStrategy{
    @Override
    public BigDecimal calculateDiscount(Order order, Rule rule) {
        BigDecimal discount =
                order.getTotalPrice()
                        .multiply(rule.getValue())
                        .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        if (rule.getMaxDiscountValue() != null) {
            discount = discount.min(rule.getMaxDiscountValue());
        }

        return discount;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.PERCENTAGE;
    }
}
