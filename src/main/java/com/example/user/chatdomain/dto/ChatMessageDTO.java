package com.example.user.chatdomain.dto;

import lombok.Data;

@Data
public class ChatMessageDTO {

    /* 보내는 사람 */
    private String sender;

    /* 메시지 내용 */
    private String message;
}
