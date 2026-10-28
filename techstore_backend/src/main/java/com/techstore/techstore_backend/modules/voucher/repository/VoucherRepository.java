package com.techstore.techstore_backend.modules.voucher.repository;

import com.techstore.techstore_backend.modules.voucher.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Integer> {
}
