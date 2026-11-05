package com.techstore.techstore_backend.modules.message.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    int senderId;
    int receiverId;
    String content;
    LocalDateTime createAt;
}
