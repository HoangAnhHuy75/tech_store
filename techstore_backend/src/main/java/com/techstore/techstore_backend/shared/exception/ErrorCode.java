package com.techstore.techstore_backend.shared.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    USER_EXIST(1001,"Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(1002,"Người dùng không tồn tại", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1003,"Không thể xác thực",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1004,"Không có quyền truy cập",HttpStatus.FORBIDDEN),

    CATEGORY_EXIST(1005,"Loại sản phẩm đã tồn tại",HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXIST(1006,"Loại sản phẩm không tô tại",HttpStatus.BAD_REQUEST),

    SPECIFICATION_NOT_EXIST(1007,"Thông số kỹ thuật không tồn tại",HttpStatus.BAD_REQUEST),

    PRODUCT_NOT_EXIST(1008,"Sản phẩm không tồn tại",HttpStatus.BAD_REQUEST),

    CAMPAIGN_NOT_EXIST(1019,"Chiến dịch không tồn tại",HttpStatus.BAD_REQUEST),
    CAMPAIGN_TIME_PAST(1010,"Thời gian start chiến dịch phải sau hiện tại",HttpStatus.BAD_REQUEST),
    CAMPAIGN_TIME_FUTURE(1011,"Thời gian end chiến dịch phải sau thời gian start",HttpStatus.BAD_REQUEST),
    CAMPAIGN_BUDGET_INVALID(1012,"Thời gian end chiến dịch phải sau thời gian start",HttpStatus.BAD_REQUEST),
    CAMPAIGN_TIME_END(1013,"Chiến dịch đã kết thúc",HttpStatus.BAD_REQUEST),
    CANNOT_UPDATE_START_TIME(1014,"Không thể cập nhật thời gian bắt đầu khi chiến dịch đang diễn ra",HttpStatus.BAD_REQUEST),
    CANNOT_UPDATE_END_TIME(1015,"Thời gian end chiến dịch phải sau thời gian hiện tại",HttpStatus.BAD_REQUEST);
    int code;
    String message;
    HttpStatus httpStatus;
}
