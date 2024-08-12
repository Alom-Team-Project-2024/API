package com.example.user.chatdomain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/pub"); // Message Broker를 위한 기본 경로
        config.setApplicationDestinationPrefixes("/sub"); // 클라이언트가 메시지를 보낼 때 사용하는 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp") // WebSocket 엔드포인트 설정
                .setAllowedOriginPatterns("*")
                .withSockJS(); // SockJS를 통해 WebSocket을 지원하지 않는 브라우저를 지원
    }

}
