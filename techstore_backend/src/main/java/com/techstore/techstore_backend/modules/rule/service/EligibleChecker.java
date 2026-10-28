package com.techstore.techstore_backend.modules.rule.service;

import com.techstore.techstore_backend.modules.order.entity.Order;
import com.techstore.techstore_backend.modules.rule.entity.Rule;
import com.techstore.techstore_backend.modules.rule.enums.RuleStatus;
import com.techstore.techstore_backend.modules.user.entity.User;
import com.techstore.techstore_backend.modules.user.enums.UserType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EligibleChecker {
    public boolean isEligible(Order order, Rule rule, User user) {
        return checkMinOrder(order,rule)
                && checkUserType(rule,user)
                && checkRuleStatus(rule)
                && checkTime(rule);
    }

    public boolean checkUserType(Rule rule, User user){
        UserType userType = rule.getUserType();
        if(userType.equals(UserType.ALL) || userType.equals(UserType.NORMAL))
            return true;
        if(user.getUserType() == null || !user.getUserType().equals(userType)){
            return false;
        }
        return true;
    }

    public boolean checkRuleStatus(Rule rule) {
        if (rule.getStatus().equals(RuleStatus.ACTIVE) && !rule.getIsDeleted()) {
            return true;
        }
        return false;
    }

    public boolean checkTime(Rule rule) {
        LocalDateTime now = LocalDateTime.now();
        if (rule.getStartTime() != null && now.isBefore(rule.getStartTime())) {
            return false;
        }
        if (rule.getEndTime() != null && now.isAfter(rule.getEndTime())) {
            return false;
        }
        return true;
    }

    public boolean checkMinOrder(Order order, Rule rule) {
        if (rule.getMinOrderValue() == null) {
            return true;
        }
        return order.getTotalPrice().compareTo(rule.getMinOrderValue()) >= 0;
    }
}
