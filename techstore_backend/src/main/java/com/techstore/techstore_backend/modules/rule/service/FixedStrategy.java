package com.techstore.techstore_backend.modules.rule.service;

import com.techstore.techstore_backend.modules.order.entity.Order;
import com.techstore.techstore_backend.modules.rule.entity.Rule;
import com.techstore.techstore_backend.modules.rule.enums.RuleType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FixedStrategy implements RuleStrategy{

    @Override
    public RuleType getRuleType(){
        return RuleType.FIXED;
    }

    @Override
    public BigDecimal calculateDiscount(Order order, Rule rule){
        return rule.getValue().min(order.getTotalPrice());
    }
}
