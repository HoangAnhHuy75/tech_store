package com.techstore.techstore_backend.modules.user.repository;

import com.techstore.techstore_backend.modules.user.entity.UserVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserVoucherRepository extends JpaRepository<UserVoucher, Integer> {
    public boolean existsByUserIdAndVoucherId(Integer userId, Integer voucherId);
    public Optional<UserVoucher> findByUserIdAndVoucherId(Integer userId, Integer voucherId);
}
