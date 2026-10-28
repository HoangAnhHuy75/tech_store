package com.techstore.techstore_backend.modules.rule.service;

import com.techstore.techstore_backend.modules.campaign.entity.Campaign;
import com.techstore.techstore_backend.modules.campaign.repository.CampaignRepository;
import com.techstore.techstore_backend.modules.rule.dto.request.RuleRequest;
import com.techstore.techstore_backend.modules.rule.dto.response.RuleResponse;
import com.techstore.techstore_backend.modules.rule.entity.Rule;
import com.techstore.techstore_backend.modules.rule.enums.RuleStatus;
import com.techstore.techstore_backend.modules.rule.repository.RuleRepository;
import com.techstore.techstore_backend.modules.user.enums.UserType;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final RuleRepository ruleRepository;
    private final CampaignRepository campaignRepository;

    public RuleResponse createRule(RuleRequest request) {
        Campaign campaign = campaignRepository.findById(request.getCampaignId())
                .orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXIST));

        validateTime(request.getStartTime(), request.getEndTime(), campaign);

        Rule rule = Rule.builder()
                .name(request.getName())
                .campaign(campaign)
                .ruleType(request.getRuleType())
                .userType(request.getUserType())
                .value(request.getValue())
                .maxDiscountValue(request.getMaxDiscountValue())
                .minOrderValue(request.getMinOrderValue())
                .payload(request.getPayload())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        return mapToRuleResponse(ruleRepository.save(rule));
    }

    public List<RuleResponse> findAll() {
        List<Rule> listRule = ruleRepository.findAll();
        return listRule.stream()
                .map(this::mapToRuleResponse)
                .toList();
    }

    public void deleteRule(Integer id) {
        Rule rule = ruleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.RULE_NOT_EXIST));
        rule.setIsDeleted(true);
        ruleRepository.save(rule);
    }

    public void validateTime(LocalDateTime startTime, LocalDateTime endTime, Campaign campaign){
        LocalDateTime startTimeCampaign = campaign.getStartTime();
        LocalDateTime endTimeCampaign = campaign.getEndTime();
        if(startTime.isBefore(startTimeCampaign)){
            throw new AppException(ErrorCode.RULE_START_TIME_INVALID);
        }
        if(endTime.isAfter(endTimeCampaign)){
            throw new AppException(ErrorCode.RULE_END_TIME_INVALID);
        }
        if(!endTime.isAfter(startTime)){
            throw new AppException(ErrorCode.RULE_TIME_INVALID);
        }
    }

    private RuleResponse mapToRuleResponse(Rule rule) {
        return RuleResponse.builder()
                .id(rule.getId())
                .name(rule.getName())
                .campaign(rule.getCampaign())
                .ruleType(rule.getRuleType())
                .value(rule.getValue())
                .maxDiscountValue(rule.getMaxDiscountValue())
                .minOrderValue(rule.getMinOrderValue())
                .payload(rule.getPayload())
                .startTime(rule.getStartTime())
                .endTime(rule.getEndTime())
                .status(rule.getStatus())
                .userType(rule.getUserType())
                .isDelete(rule.getIsDeleted())
                .createAt(rule.getCreatedAt())
                .build();
    }
}
