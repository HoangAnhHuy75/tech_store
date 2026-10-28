package com.techstore.techstore_backend.modules.campaign.service;

import com.techstore.techstore_backend.modules.campaign.dto.request.CampaignRequest;
import com.techstore.techstore_backend.modules.campaign.dto.response.CampaignResponse;
import com.techstore.techstore_backend.modules.campaign.entity.Campaign;
import com.techstore.techstore_backend.modules.campaign.enums.CampaignStatus;
import com.techstore.techstore_backend.modules.campaign.repository.CampaignRepository;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public CampaignResponse createCampaign(CampaignRequest request) {
        validateCampaignTime(request.getStartTime(), request.getEndTime());
        validateCampaignBudget(request.getBudget());

        Campaign campaign = Campaign.builder()
                        .name(request.getName())
                        .startTime(request.getStartTime())
                        .endTime(request.getEndTime())
                        .budget(request.getBudget())
                        .build();
        return mapToCampaignResponse(campaignRepository.save(campaign));
    }

    public CampaignResponse updateCampaign(CampaignRequest request, Integer id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXIST));
        if (campaign.getStatus().equals(CampaignStatus.END)) {
            throw new AppException(ErrorCode.CAMPAIGN_TIME_END);
        }
        if (campaign.getStatus().equals(CampaignStatus.ACTIVE)) {
            if (!campaign.getStartTime().equals(request.getStartTime())) {
                throw new AppException(ErrorCode.CANNOT_UPDATE_START_TIME);
            }
            if (campaign.getEndTime().isBefore(LocalDateTime.now())) {
                throw new AppException(ErrorCode.CANNOT_UPDATE_END_TIME);
            }
        } else if (campaign.getStatus().equals(CampaignStatus.UPCOMING)) {
            validateCampaignTime(request.getStartTime(), request.getEndTime());
        }
        validateCampaignBudget(request.getBudget());
        campaign.setBudget(request.getBudget());
        campaign.setName(request.getName());
        campaign.setStartTime(request.getStartTime());
        campaign.setEndTime(request.getEndTime());
        return mapToCampaignResponse(campaignRepository.save(campaign));
    }

    public List<CampaignResponse> findAll(Integer id) {
        List<Campaign> list = campaignRepository.findAll();
        return list.stream().map(this::mapToCampaignResponse).toList();
    }

    public CampaignResponse findById(Integer id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXIST));
        return mapToCampaignResponse(campaign);
    }

    public void validateCampaignTime(LocalDateTime startTime, LocalDateTime endTime){
        if(startTime.isBefore(LocalDateTime.now())){
            throw new AppException(ErrorCode.CAMPAIGN_TIME_PAST);
        }
        if(endTime.isBefore(startTime)) {
            throw new AppException(ErrorCode.CAMPAIGN_TIME_FUTURE);
        }
    }

    private void validateCampaignBudget(BigDecimal budget) {
        if (budget.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.CAMPAIGN_BUDGET_INVALID);
        }
    }

    private CampaignResponse mapToCampaignResponse(Campaign campaign) {
        return CampaignResponse.builder()
                .id(campaign.getId())
                .name(campaign.getName())
                .startTime(campaign.getStartTime())
                .endTime(campaign.getEndTime())
                .budget(campaign.getBudget())
                .status(campaign.getStatus())
                .createdAt(campaign.getCreatedAt())
                .updatedAt(campaign.getUpdatedAt())
                .isDelete(campaign.isDeleted())
                .build();
    }

}
