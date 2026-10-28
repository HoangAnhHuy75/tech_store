package com.techstore.techstore_backend.modules.rule.service;

import com.techstore.techstore_backend.modules.rule.enums.RuleType;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StrategyFactory {
    private Map<RuleType, RuleStrategy> strategies;

    public StrategyFactory(List<RuleStrategy> strategies){
        this.strategies = strategies.stream().collect(Collectors.toMap(s -> s.getRuleType(),s -> s));
    }

    public RuleStrategy resolveStrategy(RuleType ruleType){
        if(!strategies.containsKey(ruleType)){
            throw new AppException(ErrorCode.RULE_TYPE_NOT_EXIST);
        }
        return this.strategies.get(ruleType);
    }
}
