package com.example.user.chatdomain.config;

import com.example.user.userdomain.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JWTUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("jwt channel interceptor 시작");
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        log.info("accessor = {}", accessor);

        log.info("Received STOMP command : {}", accessor.getCommand());

        if (accessor.getCommand() == null) {
            throw new NullPointerException();
        }

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            log.info("Processing CONNECT command");

            String authorization = accessor.getFirstNativeHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                log.warn("Authorization header is missing or invalid.");
                return null; // 메시지를 차단
            }

            log.info("authorization : {}", authorization);
            // Extract the token
            String token = authorization.split(" ")[1];

            if (jwtUtil.isExpired(token)) {
                log.warn("JWT token is expired.");
                return null; // 메시지를 차단
            }

            String username = jwtUtil.getUsername(token);
            accessor.setUser(() -> username); // WebSocket 세션에 사용자 이름 설정
            log.info("ws channel interceptor 통과");
        }

        return message;
    }
}
