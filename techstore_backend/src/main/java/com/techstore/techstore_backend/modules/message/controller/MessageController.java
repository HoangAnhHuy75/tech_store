package com.techstore.techstore_backend.modules.message.controller;

import com.techstore.techstore_backend.modules.message.dto.request.MessageRequest;
import com.techstore.techstore_backend.modules.message.dto.response.MessageResponse;
import com.techstore.techstore_backend.modules.message.service.MessageService;
import com.techstore.techstore_backend.modules.user.dto.response.UserResponse;
import com.techstore.techstore_backend.modules.user.entity.User;
import com.techstore.techstore_backend.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping
    ResponseEntity<ApiResponse<MessageResponse>> createMessage(@RequestBody @Valid MessageRequest request){
        MessageResponse messageResponse = messageService.createMessage(request);
        String receiverId = String.valueOf(request.getReceiverId());
        simpMessagingTemplate.convertAndSendToUser(
                receiverId,
                "/queue/messages",
                messageResponse
        );
        return ResponseEntity.ok(ApiResponse.<MessageResponse>builder()
                        .message("Success")
                        .result(messageResponse)
                        .build()
        );
    }
    @GetMapping("/conversation")
    ResponseEntity<ApiResponse<List<MessageResponse>>> getConversation(@RequestParam int senderId,@RequestParam int receiverId){
        List<MessageResponse> result = messageService.getTwoMessage(receiverId,senderId);
        return ResponseEntity.ok(
                ApiResponse.<List<MessageResponse>>builder()
                        .message("Success")
                        .result(result)
                        .build()
        );
    }

    @GetMapping("/{userId}")
    ResponseEntity<ApiResponse<List<UserResponse>>> getConversation(@PathVariable int userId, @RequestParam(required = false) String key){
        List<UserResponse> messageList = messageService.getLastestMessageForUser(userId,key);
        return ResponseEntity.ok(
                ApiResponse.<List<UserResponse>>builder()
                        .message("Success")
                        .result(messageList)
                        .build()
        );
    }
}
