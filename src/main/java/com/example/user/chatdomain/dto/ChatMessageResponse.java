package com.example.user.chatdomain.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatMessageResponse {

        /* 채팅방 Id */
        private Long chatRoomId;

        /* 보내는 사람(유저 학번) */
        @Parameter(description = "보내는 유저 학번")
        private String sender;

        /* 메시지 내용 */
        private String message;

        /* 메시지 작성 시간 */
        private LocalDateTime createdAt;
}
