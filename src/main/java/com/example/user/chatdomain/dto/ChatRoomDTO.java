package com.example.user.chatdomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomDTO {

    /* 채팅방 이름 */
    @Schema(description = "채팅방 이름")
    private String chatRoomName;
}
