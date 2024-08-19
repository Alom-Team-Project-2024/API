package com.example.user.chatdomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatRoomDTO {

    /* 채팅방 이름 */
    @Schema(description = "채팅방 이름")
    private String chatRoomName;

    /* 채팅방 생성 시간 */
    @Schema(description = "채팅방 생성 시간")
    private LocalDateTime createdAt;

    /* 채팅방 수정 시간(마지막 메시지가 보내진 시간) */
    @Schema(description = "채팅방 수정 시간(마지막 메시지가 보내진 시간)")
    private LocalDateTime modifiedAt;
}
