package com.example.user.chatdomain.service;

import com.example.user.chatdomain.dto.ChatMessageDTO;
import com.example.user.chatdomain.dto.ChatRoomDTO;
import com.example.user.chatdomain.entity.ChatMessage;
import com.example.user.chatdomain.entity.ChatRoom;
import com.example.user.chatdomain.repository.ChatMessageRepository;
import com.example.user.chatdomain.repository.ChatRoomRepository;
import com.example.user.userdomain.dto.UserResponse;
import com.example.user.userdomain.service.SejongAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OneToOneChatService {

    private final ChatRoomService chatRoomService;
    private final SejongAuthService sejongAuthService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    /* 1대1 채팅방 생성 */
    public ChatRoomDTO createChatRoomForUsers(Long id1, Long id2) {

        UserResponse userResponse1 = sejongAuthService.findUser(id1);
        UserResponse userResponse2 = sejongAuthService.findUser(id2);


        String chatRoomName = userResponse1.getNickname() + userResponse2.getNickname();
        ChatRoom chatRoom = chatRoomService.createChatRoom(chatRoomName);

        chatRoomService.addUserToChatRoom(userResponse1, chatRoom);
        chatRoomService.addUserToChatRoom(userResponse2, chatRoom);

        return new ChatRoomDTO(chatRoom.getChatRoomName(), chatRoom.getCreatedAt(), chatRoom.getModifiedAt());
    }

    /* 1대1 채팅방에서 메시지 보내는 로직 */
    public ChatMessage sendMessageToChatRoom(ChatMessageDTO chatMessageDTO) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByChatRoomName(chatMessageDTO.getSender()).orElseThrow();
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(chatRoom.getId().toString())
                .sender(chatMessageDTO.getSender())
                .message(chatMessageDTO.getMessage())
                .build();

        // DB에 메시지 저장
        return chatMessageRepository.save(chatMessage);
    }
}
