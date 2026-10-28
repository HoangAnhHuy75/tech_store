package com.techstore.techstore_backend.modules.voucher.controller;

import com.techstore.techstore_backend.modules.voucher.dto.request.VoucherCreateRequest;
import com.techstore.techstore_backend.modules.voucher.dto.request.VoucherUpdateRequest;
import com.techstore.techstore_backend.modules.voucher.dto.response.VoucherResponse;
import com.techstore.techstore_backend.modules.voucher.service.VoucherService;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity<ApiResponse<VoucherResponse>> createVoucher(@RequestBody VoucherCreateRequest request) {
        VoucherResponse response = voucherService.createVoucher(request);
        return ResponseEntity.ok(ApiResponse.<VoucherResponse>builder()
                .code(201)
                .message("Tạo voucher thành công")
                .result(response)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VoucherResponse>>> findAll() {
        List<VoucherResponse> response = voucherService.findAll();
        return ResponseEntity.ok(ApiResponse.<List<VoucherResponse>>builder()
                .code(200)
                .message("Lấy danh sách voucher thành công")
                .result(response)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VoucherResponse>> findById(@PathVariable Integer id) {
        VoucherResponse response = voucherService.findById(id);
        return ResponseEntity.ok(ApiResponse.<VoucherResponse>builder()
                .code(200)
                .message("Lấy voucher thành công")
                .result(response)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VoucherResponse>> update(@PathVariable Integer id, @RequestBody VoucherUpdateRequest request) {
        VoucherResponse response = voucherService.updateVoucher(id, request);
        return ResponseEntity.ok(ApiResponse.<VoucherResponse>builder()
                .code(200)
                .message("Cập nhật voucher thành công")
                .result(response)
                .build());
    }
}
