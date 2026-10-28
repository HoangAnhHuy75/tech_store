package com.techstore.techstore_backend.modules.order.entity;

import com.techstore.techstore_backend.modules.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Table(name = "order_items")
@NoArgsConstructor
@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    BigDecimal price;
}
