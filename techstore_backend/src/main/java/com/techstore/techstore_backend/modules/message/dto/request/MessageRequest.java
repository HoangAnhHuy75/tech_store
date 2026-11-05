package com.techstore.techstore_backend.modules.message.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    @NotNull(message = "Sender ID không được để trống")
    Integer senderId;

    @NotNull(message = "Receiver ID không được để trống")
    Integer receiverId;

    @NotBlank(message = "không thể gửi khi chưa có nội dung")
    String content;
}
