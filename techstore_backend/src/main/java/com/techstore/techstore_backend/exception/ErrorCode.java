package com.techstore.techstore_backend.exception;

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
    CATEGORY_NOT_EXIST(1006,"Loại sản phẩm không tô tại",HttpStatus.BAD_REQUEST);
    int code;
    String message;
    HttpStatus httpStatus;
}
