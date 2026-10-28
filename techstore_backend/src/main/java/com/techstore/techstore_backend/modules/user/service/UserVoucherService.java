package com.techstore.techstore_backend.modules.user.service;

import com.techstore.techstore_backend.modules.user.dto.request.UserVoucherRequest;
import com.techstore.techstore_backend.modules.user.dto.response.UserVoucherResponse;
import com.techstore.techstore_backend.modules.user.entity.User;
import com.techstore.techstore_backend.modules.user.entity.UserVoucher;
import com.techstore.techstore_backend.modules.user.repository.UserRepository;
import com.techstore.techstore_backend.modules.user.repository.UserVoucherRepository;
import com.techstore.techstore_backend.modules.voucher.entity.Voucher;
import com.techstore.techstore_backend.modules.voucher.repository.VoucherRepository;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserVoucherService {
    private final UserVoucherRepository userVoucherRepository;
    private final UserRepository userRepository;
    private final VoucherRepository voucherRepository;

    public UserVoucherResponse create(UserVoucherRequest request) {
        User user = getUser();
        Voucher voucher = getVoucher(request.getVoucherId());
        if (userVoucherRepository.existsByUserIdAndVoucherId(user.getId(), voucher.getId())) {
            throw new AppException(ErrorCode.USER_VOUCHER_ALREADY_CLAIMED);
        }
        UserVoucher userVoucher = UserVoucher.builder()
                .user(user)
                .voucher(voucher)
                .build();
        return mapToUserVoucherResponse(userVoucherRepository.save(userVoucher));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserVoucherResponse> findAll() {
        List<UserVoucher> list = userVoucherRepository.findAll();
        return list.stream().map(this::mapToUserVoucherResponse).toList();
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
    }

    public Voucher getVoucher(Integer voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXIST));
    }

    public UserVoucherResponse mapToUserVoucherResponse(UserVoucher userVoucher) {
        return UserVoucherResponse.builder()
                .id(userVoucher.getId())
                .voucher(userVoucher.getVoucher())
                .collectedAt(userVoucher.getCollectedAt())
                .status(userVoucher.getStatus())
                .build();
    }
}
