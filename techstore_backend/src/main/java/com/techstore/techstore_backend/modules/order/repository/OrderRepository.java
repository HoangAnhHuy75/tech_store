package com.techstore.techstore_backend.modules.order.repository;

import com.techstore.techstore_backend.modules.order.entity.Order;
import com.techstore.techstore_backend.modules.order.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    public long countByUserIdAndVoucherIdAndStatus(Integer userId, Integer orderId, OrderStatus status);
    public List<Order> findByCreatedAtGreaterThanEqualAndCreatedAtLessThanAndIsDeletedFalse(
            LocalDateTime start, LocalDateTime end);
}
