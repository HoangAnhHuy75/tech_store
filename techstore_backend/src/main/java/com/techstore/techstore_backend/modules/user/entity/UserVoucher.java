package com.techstore.techstore_backend.modules.user.entity;

import com.techstore.techstore_backend.modules.user.enums.UserVoucherStatus;
import com.techstore.techstore_backend.modules.voucher.entity.Voucher;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_vouchers")
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    // Quan hệ N-1 với bảng "user"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    // Quan hệ N-1 với bảng voucher
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id", nullable = false)
    Voucher voucher;

    @CreationTimestamp
    @Column(name = "collected_at", nullable = false, updatable = false)
    LocalDateTime collectedAt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    UserVoucherStatus status = UserVoucherStatus.UNUSED;
}
