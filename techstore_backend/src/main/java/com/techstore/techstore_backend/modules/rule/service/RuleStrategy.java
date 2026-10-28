package com.techstore.techstore_backend.modules.rule.service;

import com.techstore.techstore_backend.modules.order.entity.Order;
import com.techstore.techstore_backend.modules.rule.entity.Rule;
import com.techstore.techstore_backend.modules.rule.enums.RuleType;

import java.math.BigDecimal;

public interface RuleStrategy {
    public BigDecimal calculateDiscount(Order order, Rule rule);
    public RuleType getRuleType();
}
