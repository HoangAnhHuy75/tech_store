package com.techstore.techstore_backend.modules.order.service;

import com.techstore.techstore_backend.modules.order.dto.request.OrderItemRequest;
import com.techstore.techstore_backend.modules.order.dto.request.OrderRequest;
import com.techstore.techstore_backend.modules.order.dto.response.OrderResponse;
import com.techstore.techstore_backend.modules.order.entity.Order;
import com.techstore.techstore_backend.modules.order.entity.OrderItem;
import com.techstore.techstore_backend.modules.order.enums.OrderStatus;
import com.techstore.techstore_backend.modules.order.repository.OrderItemRepository;
import com.techstore.techstore_backend.modules.order.repository.OrderRepository;
import com.techstore.techstore_backend.modules.product.entity.Product;
import com.techstore.techstore_backend.modules.product.repository.ProductRepository;
import com.techstore.techstore_backend.modules.rule.service.RuleEngine;
import com.techstore.techstore_backend.modules.user.entity.User;
import com.techstore.techstore_backend.modules.user.entity.UserVoucher;
import com.techstore.techstore_backend.modules.user.enums.UserVoucherStatus;
import com.techstore.techstore_backend.modules.user.repository.UserRepository;
import com.techstore.techstore_backend.modules.user.repository.UserVoucherRepository;
import com.techstore.techstore_backend.modules.voucher.entity.Voucher;
import com.techstore.techstore_backend.modules.voucher.enums.VoucherStatus;
import com.techstore.techstore_backend.modules.voucher.repository.VoucherRepository;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final VoucherRepository voucherRepository;
    private final DistributedLockService distributedLockService;
    private final TransactionTemplate transactionTemplate;
    private final UserVoucherRepository userVoucherRepository;
    private final RuleEngine ruleEngine;

    public OrderResponse createOrder(OrderRequest request) {
        if (request.getVoucherId() == null) {
            return createOrderWithOutVoucher(request);
        }
        String lockKey = "lock:voucher:" + request.getVoucherId();
        String lockValue = UUID.randomUUID().toString();
        boolean isLocked = distributedLockService.tryLock(lockKey, lockValue, 10);
        if (!isLocked) {
            throw new AppException(ErrorCode.VOUCHER_BUSY);
        }
        try {
            return createOrderVoucher(request);
        } finally {
            distributedLockService.unLock(lockKey, lockValue);
        }
    }

    private OrderResponse createOrderWithOutVoucher(OrderRequest request) {
        return transactionTemplate.execute(
                status -> {
                    User user = getCurrentUser();
                    Order order = orderRepository.save(Order.builder()
                                            .user(user)
                                            .totalPrice(BigDecimal.ZERO)
                                            .finalPrice(BigDecimal.ZERO)
                                            .voucher(null)
                                            .build());
                    BigDecimal totalPrice =
                            createOrderItemAndCalculateTotal(order, request.getItems());
                    order.setTotalPrice(totalPrice);
                    order.setFinalPrice(totalPrice);
                    return mapToOrderResponse(order);
                });
    }

    private OrderResponse createOrderVoucher(OrderRequest request) {
        return transactionTemplate.execute(
                status -> {
                    User user = getCurrentUser();
                    Voucher currentVoucher = voucherRepository.findById(request.getVoucherId()).orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXIST));
                    UserVoucher userVoucher =
                            userVoucherRepository
                                    .findByUserIdAndVoucherId(user.getId(), currentVoucher.getId())
                                    .orElseThrow(
                                            () ->
                                                    new AppException(
                                                            ErrorCode
                                                                    .USER_VOUCHER_NOT_EXIST));

                    validateGenericVoucher(currentVoucher, userVoucher);

                    Order order =
                            orderRepository.save(
                                    Order.builder()
                                            .user(user)
                                            .voucher(currentVoucher)
                                            .totalPrice(BigDecimal.ZERO)
                                            .finalPrice(BigDecimal.ZERO)
                                            .build());
                    BigDecimal totalPrice =
                            createOrderItemAndCalculateTotal(order, request.getItems());
                    order.setTotalPrice(totalPrice);
                    BigDecimal discount =
                            ruleEngine.applyRules(order, currentVoucher.getRule(), user);
                    order.setFinalPrice(totalPrice.subtract(discount));
                    consumeVoucher(currentVoucher, userVoucher);
                    return mapToOrderResponse(order);
                });
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
    }

    public void validateGenericVoucher(Voucher voucher, UserVoucher userVoucher) {
        if (!voucher.getStatus().equals(VoucherStatus.ACTIVE)) {
            throw new AppException(ErrorCode.VOUCHER_STATUS_INVALID);
        }
        if (voucher.getQuantityRemain() <= 0) {
            throw new AppException(ErrorCode.VOUCHER_OUT_OF_STOCK);
        }
        if (userVoucher.getStatus().equals(UserVoucherStatus.USED)) {
            throw new AppException(ErrorCode.USER_VOUCHER_ALREADY_USED);
        }
    }

    private BigDecimal createOrderItemAndCalculateTotal(
            Order order, List<OrderItemRequest> requests) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Integer> productIds = requests.stream().map(OrderItemRequest::getProductId).distinct().toList();
        List<Product> products = productRepository.findAllById(productIds);
        Map<Integer, Product> productMaps = products.stream().collect(Collectors.toMap(Product::getId, p -> p));
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemRequest request : requests) {
            Product product = productMaps.get(request.getProductId());
            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            OrderItem item = OrderItem.builder()
                            .order(order)
                            .product(product)
                            .quantity(request.getQuantity())
                            .price(product.getPrice())
                            .build();
            items.add(item);
            orderItemRepository.save(item);
        }
        order.setItems(items);
        return totalPrice;
    }

    private void consumeVoucher(Voucher voucher, UserVoucher userVoucher) {

        voucher.setQuantityRemain(voucher.getQuantityRemain() - 1);
        if (voucher.getQuantityRemain() == 0) {
            voucher.setStatus(VoucherStatus.DISABLED);
        }
        long usedCount = orderRepository.countByUserIdAndVoucherIdAndStatus(userVoucher.getUser().getId(), voucher.getId(), OrderStatus.SUCCESS);
        if (usedCount >= voucher.getLimitClient()) {
            userVoucher.setStatus(UserVoucherStatus.USED);
        }

    }

    public List<OrderResponse> findAll() {
        List<Order> listOder = orderRepository.findAll();
        return listOder.stream().map(this::mapToOrderResponse).toList();
    }

    public OrderResponse findById(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXIST));
        return mapToOrderResponse(order);
    }

    public List<OrderResponse> filterByTime(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new AppException(ErrorCode.ORDER_DATE_RANGE_INVALID);
        }
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();
        return orderRepository
                .findByCreatedAtGreaterThanEqualAndCreatedAtLessThanAndIsDeletedFalse(start, end)
                .stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    public OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .discount(order.getTotalPrice().subtract(order.getFinalPrice()))
                .finalPrice(order.getFinalPrice())
                .build();
    }
}
