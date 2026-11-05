package com.techstore.techstore_backend.modules.message.service;

import com.techstore.techstore_backend.modules.message.dto.request.MessageRequest;
import com.techstore.techstore_backend.modules.message.dto.response.MessageResponse;
import com.techstore.techstore_backend.modules.message.entity.Message;
import com.techstore.techstore_backend.modules.message.repository.MessageRepository;
import com.techstore.techstore_backend.modules.user.dto.response.UserResponse;
import com.techstore.techstore_backend.modules.user.entity.User;
import com.techstore.techstore_backend.modules.user.repository.UserRepository;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageResponse createMessage(MessageRequest request){
        User sender = userRepository.findById(request.getSenderId()).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
        User receiver = userRepository.findById(request.getReceiverId()).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
        Message message = Message.builder()
                .content(request.getContent())
                .sender(sender)
                .receiver(receiver)
                .build();
        return mapToMessageResponse(messageRepository.save(message));
    }

    public List<MessageResponse> getTwoMessage(Integer senderId, Integer receiverId){
        List<Message> messages = messageRepository.getConversation(senderId, receiverId);
        return messages.stream().map(this::mapToMessageResponse).toList();
    }

    public List<UserResponse> getLastestMessageForUser(Integer userId, String keyWord){
        List<User> users = messageRepository.getLastestSenders(userId,keyWord);
        return users.stream().map(this::mapToUserResponse).toList();
    }

    public MessageResponse mapToMessageResponse(Message message){
        return MessageResponse.builder()
                .content(message.getContent())
                .receiverId(message.getReceiver().getId())
                .senderId(message.getSender().getId())
                .createAt(message.getCreatedAt())
                .build();
    }

    public UserResponse mapToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
