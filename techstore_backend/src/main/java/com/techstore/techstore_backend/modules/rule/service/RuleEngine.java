package com.techstore.techstore_backend.modules.rule.service;

import com.techstore.techstore_backend.modules.order.entity.Order;
import com.techstore.techstore_backend.modules.rule.entity.Rule;
import com.techstore.techstore_backend.modules.user.entity.User;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RuleEngine {
    private final StrategyFactory strategyFactory;
    private final EligibleChecker eligibleChecker;

    public BigDecimal applyRules(Order order, Rule rule, User user) {
        if (!eligibleChecker.isEligible(order, rule, user)) {
            throw new AppException(ErrorCode.RULE_NOT_ELIGIBLE);
        }
        RuleStrategy ruleStrategy = strategyFactory.resolveStrategy(rule.getRuleType());
        return ruleStrategy.calculateDiscount(order, rule);
    }
}
