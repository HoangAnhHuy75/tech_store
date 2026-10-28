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
    USER_EXIST(1001, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(1002, "Người dùng không tồn tại", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1003, "Không thể xác thực", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1004, "Không có quyền truy cập", HttpStatus.FORBIDDEN),

    CATEGORY_EXIST(1005, "Loại sản phẩm đã tồn tại", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXIST(1006, "Loại sản phẩm không tô tại", HttpStatus.BAD_REQUEST),

    SPECIFICATION_NOT_EXIST(1007, "Thông số kỹ thuật không tồn tại", HttpStatus.BAD_REQUEST),

    PRODUCT_NOT_EXIST(1008, "Sản phẩm không tồn tại", HttpStatus.BAD_REQUEST),


    RULE_TYPE_NOT_EXIST(1009, "RuleType không tồn tại", HttpStatus.BAD_REQUEST),
    RULE_NOT_EXIST(1010, "Rule không tồn tại", HttpStatus.BAD_REQUEST),
    RULE_NOT_ELIGIBLE(1011, "Rule không đủ điều kiện", HttpStatus.BAD_REQUEST),
    RULE_MISS_PAYLOAD(1012, "Rule không có thông tin mua X tặng Y", HttpStatus.BAD_REQUEST),
    RULE_START_TIME_INVALID(1013, "Thời gian bắt đầu của rule phải >= thời gian bắt đầu chiến dịch", HttpStatus.BAD_REQUEST),
    RULE_END_TIME_INVALID(1014, "Thời gian kết thúc của rule phải <= thời gian kết thúc chiến dịch", HttpStatus.BAD_REQUEST),
    RULE_TIME_INVALID(1015, "Thời gian kết thúc rule phải sau thời gian bắt đầu rule", HttpStatus.BAD_REQUEST),

    CAMPAIGN_NOT_EXIST(1019, "Chiến dịch không tồn tại", HttpStatus.BAD_REQUEST),
    CAMPAIGN_TIME_PAST(1020, "Thời gian start chiến dịch phải sau hiện tại", HttpStatus.BAD_REQUEST),
    CAMPAIGN_TIME_FUTURE(1021, "Thời gian end chiến dịch phải sau thời gian start", HttpStatus.BAD_REQUEST),
    CAMPAIGN_BUDGET_INVALID(1022, "Thời gian end chiến dịch phải sau thời gian start", HttpStatus.BAD_REQUEST),
    CAMPAIGN_TIME_END(1023, "Chiến dịch đã kết thúc", HttpStatus.BAD_REQUEST),
    CANNOT_UPDATE_START_TIME(1024, "Không thể cập nhật thời gian bắt đầu khi chiến dịch đang diễn ra", HttpStatus.BAD_REQUEST),
    CANNOT_UPDATE_END_TIME(1025, "Thời gian end chiến dịch phải sau thời gian hiện tại", HttpStatus.BAD_REQUEST),

    VOUCHER_NOT_EXIST(1026, "Voucher không tồn tại", HttpStatus.BAD_REQUEST),
    VOUCHER_QUANTITY_INVALID(1027, "Số lượng mới không được nhỏ hơn số lượt đã sử dụng", HttpStatus.BAD_REQUEST),
    VOUCHER_RULE_NOT_EDITABLE(1028,"Không thể cập nhật rule của voucher vì voucher đã được sử dụng",HttpStatus.BAD_REQUEST),
    VOUCHER_BUSY(1029,"Voucher busy",HttpStatus.BAD_REQUEST),
    VOUCHER_OUT_OF_STOCK(1030,"Voucher đã hết",HttpStatus.BAD_REQUEST),
    VOUCHER_STATUS_INVALID(1031,"Voucher không còn khả năng sử dụng",HttpStatus.BAD_REQUEST),

    USER_VOUCHER_ALREADY_CLAIMED(1032,"Bạn đã lưu voucher này rồi",HttpStatus.BAD_REQUEST),
    USER_VOUCHER_NOT_EXIST(1033,"Không tìm thấy User Voucher",HttpStatus.BAD_REQUEST),
    USER_VOUCHER_ALREADY_USED(1034,"Bạn đã sử dụng hết lượt voucher",HttpStatus.BAD_REQUEST),

    ORDER_NOT_EXIST(1035,"Không tìm thấy Order",HttpStatus.BAD_REQUEST),
    ORDER_DATE_RANGE_INVALID(1036, "Khoảng thời gian lọc đơn hàng không hợp lệ", HttpStatus.BAD_REQUEST);
    int code;
    String message;
    HttpStatus httpStatus;
}
