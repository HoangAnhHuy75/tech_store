package com.techstore.techstore_backend.modules.order.controller;

import com.techstore.techstore_backend.modules.order.dto.request.OrderRequest;
import com.techstore.techstore_backend.modules.order.dto.response.OrderResponse;
import com.techstore.techstore_backend.modules.order.service.OrderService;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok(
                ApiResponse.<OrderResponse>builder()
                        .code(201)
                        .message("Tạo đơn hàng thành công")
                        .result(response)
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> findAll() {
        List<OrderResponse> response = orderService.findAll();
        return ResponseEntity.ok(
                ApiResponse.<List<OrderResponse>>builder()
                        .code(200)
                        .message("Lấy danh sách đơn hàng thành công")
                        .result(response)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> findById(@PathVariable Integer id) {
        OrderResponse response = orderService.findById(id);
        return ResponseEntity.ok(
                ApiResponse.<OrderResponse>builder()
                        .code(200)
                        .message("Lấy thông tin đơn hàng thành công")
                        .result(response)
                        .build());
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> filterByTime(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<OrderResponse> response = orderService.filterByTime(startDate, endDate);
        return ResponseEntity.ok(
                ApiResponse.<List<OrderResponse>>builder()
                        .code(200)
                        .message("Lọc đơn hàng theo thời gian thành công")
                        .result(response)
                        .build());
    }
}
