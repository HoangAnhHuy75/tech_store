package com.techstore.techstore_backend.shared.config;

import com.techstore.techstore_backend.modules.message.component.CustomHandshakeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final CustomHandshakeHandler customHandshakeHandler;

    // Cấu hình broker message (người trung gian) phân phối tin nhắn giữa client và backend
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //Bật Simple Broker để phân phối tin nhắn đến các client đã subscribe /topic hoặc /queue.
        config.enableSimpleBroker("/topic","/queue");
        //Quy định tiền tố /app dành cho tin nhắn từ Client gửi đến Backend để xử lý.
        config.setApplicationDestinationPrefixes("/app");
        //Quy định tiền tố /user dành cho destination gắn với một user cụ thể.
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        // Đăng ký endpoint /ws làm địa chỉ kết nối WebSocket.
        registry.addEndpoint("/ws")
                .setHandshakeHandler(customHandshakeHandler)
                // "*" nghĩa là cho phép mọi origin (thường dùng khi phát triển).
                .setAllowedOriginPatterns("*")
                // Bật SockJS để hỗ trợ các phương thức truyền tải dự phòng khi websocket không khả dụng
                .withSockJS();
    }

}
