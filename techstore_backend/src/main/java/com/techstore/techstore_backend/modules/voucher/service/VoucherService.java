package com.techstore.techstore_backend.modules.voucher.service;

import com.techstore.techstore_backend.modules.rule.entity.Rule;
import com.techstore.techstore_backend.modules.rule.repository.RuleRepository;
import com.techstore.techstore_backend.modules.voucher.dto.request.VoucherCreateRequest;
import com.techstore.techstore_backend.modules.voucher.dto.request.VoucherUpdateRequest;
import com.techstore.techstore_backend.modules.voucher.dto.response.VoucherResponse;
import com.techstore.techstore_backend.modules.voucher.entity.Voucher;
import com.techstore.techstore_backend.modules.voucher.repository.VoucherRepository;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final RuleRepository ruleRepository;
    public VoucherResponse createVoucher(VoucherCreateRequest request) {
        Rule rule = ruleRepository.findById(request.getRuleId()).orElseThrow(()->new AppException(ErrorCode.RULE_NOT_EXIST));
        Voucher voucher = Voucher.builder()
                .rule(rule)
                .quantity(request.getQuantity())
                .quantityRemain(request.getQuantity())
                .limitClient(request.getLimitClient())
                .build();
        return mapToResponse(voucherRepository.save(voucher));
    }

    public VoucherResponse findById(Integer id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXIST));
        return mapToResponse(voucher);
    }

    public List<VoucherResponse> findAll() {
        return voucherRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }


    public VoucherResponse updateVoucher(Integer id, VoucherUpdateRequest request) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXIST));

        int usedQuantity = voucher.getQuantity() - voucher.getQuantityRemain();
        int newQuantity = request.getQuantity();

        boolean changingRule = voucher.getRule().getId().equals(request.getRuleId());

        //nếu voucher đã được dùng thì không thể cập nhật rule
        if(usedQuantity > 0 && !changingRule){
            throw new AppException(ErrorCode.VOUCHER_RULE_NOT_EDITABLE);
        }

        // Không cho tổng số lượng thấp hơn số lượt đã sử dụng
        if (newQuantity < usedQuantity) {
            throw new AppException(ErrorCode.VOUCHER_QUANTITY_INVALID);
        }
        Rule rule = ruleRepository.findById(request.getRuleId()).orElseThrow(() -> new AppException(ErrorCode.RULE_NOT_EXIST));
        voucher.setRule(rule);
        voucher.setQuantity(newQuantity);

        // Tính lại số lượng còn lại
        voucher.setQuantityRemain(newQuantity - usedQuantity);

        voucher.setLimitClient(request.getLimitClient());
        voucher.setStatus(request.getStatus());
        return mapToResponse(voucherRepository.save(voucher));
    }


    private VoucherResponse mapToResponse(Voucher voucher) {
        return VoucherResponse.builder()
                .id(voucher.getId())
                .quantity(voucher.getQuantity())
                .quantityRemain(voucher.getQuantityRemain())
                .status(voucher.getStatus())
                .build();
    }
}
