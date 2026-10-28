package com.techstore.techstore_backend.modules.voucher.entity;

import com.techstore.techstore_backend.modules.rule.entity.Rule;
import com.techstore.techstore_backend.modules.voucher.enums.VoucherStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id", nullable = false)
    Rule rule;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    @Column(name = "quantity_remain", nullable = false)
    Integer quantityRemain;

    @Column(name = "limit_client", nullable = false)
    Integer limitClient;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status", nullable = false)
    VoucherStatus status = VoucherStatus.ACTIVE;
}
