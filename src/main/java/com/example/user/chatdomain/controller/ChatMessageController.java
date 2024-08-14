package com.example.user.chatdomain.controller;

import com.example.user.chatdomain.dto.ChatMessageDTO;
import com.example.user.chatdomain.entity.ChatMessage;
import com.example.user.chatdomain.service.OneToOneChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * /pub/chats/messages 경로로 메시지를 받아서
 * /sub/chats/room/{roomId} 경로로 브로드캐스팅
 */
@RestController
@AllArgsConstructor
@Tag(name = "Chat", description = "채팅 API")
public class ChatMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final OneToOneChatService oneToOneChatService;

    /* 메시지 전송 */
    @Operation(summary = "채팅 메시지 전송", description = "/pub/chats/messages 경로로 메시지를 받아서 /sub/chats/room/{roomId} 경로로 브로드캐스팅 합니다.")
    @MessageMapping("chats/messages")
    public void sendMessage(ChatMessageDTO chatMessageDTO) {

        // 메시지 저장
        ChatMessage chatMessage = oneToOneChatService.sendMessageToChatRoom(chatMessageDTO);

        // 메시지를 해당 채팅방의 구독자들에게 전송
        messagingTemplate.convertAndSend("/sub/chats/room/" + chatMessage.getChatRoomId(), chatMessage);
    }
}
