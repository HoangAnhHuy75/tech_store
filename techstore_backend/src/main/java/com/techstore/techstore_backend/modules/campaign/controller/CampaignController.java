package com.techstore.techstore_backend.modules.campaign.controller;

import com.techstore.techstore_backend.modules.campaign.dto.request.CampaignRequest;
import com.techstore.techstore_backend.modules.campaign.dto.response.CampaignResponse;
import com.techstore.techstore_backend.modules.campaign.service.CampaignService;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/campaigns")
public class CampaignController {
    private final CampaignService campaignService;

    @PostMapping
    public ResponseEntity<ApiResponse<CampaignResponse>> createCampaign(@Valid @RequestBody CampaignRequest request) {
        CampaignResponse response = campaignService.createCampaign(request);
        return ResponseEntity.ok(ApiResponse.<CampaignResponse>builder()
                .code(201)
                .message("Thêm chiến dịch thành công")
                .result(response)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CampaignResponse>>> getAllCampaigns() {
        List<CampaignResponse> response = campaignService.findAll(null);
        return ResponseEntity.ok(ApiResponse.<List<CampaignResponse>>builder()
                .code(200)
                .message("Lấy danh sách chiến dịch thành công")
                .result(response)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CampaignResponse>> getCampaignById(@PathVariable Integer id) {
        CampaignResponse response = campaignService.findById(id);
        return ResponseEntity.ok(ApiResponse.<CampaignResponse>builder()
                .code(200)
                .message("Lấy thông tin chiến dịch thành công")
                .result(response)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CampaignResponse>> updateCampaign(
            @PathVariable Integer id,
            @Valid @RequestBody CampaignRequest request) {
        CampaignResponse response = campaignService.updateCampaign(request, id);
        return ResponseEntity.ok(ApiResponse.<CampaignResponse>builder()
                .code(200)
                .message("Cập nhật chiến dịch thành công")
                .result(response)
                .build());
    }
}
