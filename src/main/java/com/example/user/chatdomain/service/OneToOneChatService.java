package com.example.user.chatdomain.service;

import com.example.user.chatdomain.dto.ChatMessageDTO;
import com.example.user.chatdomain.dto.ChatMessageResponse;
import com.example.user.chatdomain.dto.ChatRoomResponse;
import com.example.user.chatdomain.entity.ChatMessage;
import com.example.user.chatdomain.entity.ChatRoom;
import com.example.user.chatdomain.repository.ChatMessageRepository;
import com.example.user.chatdomain.repository.ChatRoomRepository;
import com.example.user.userdomain.dto.UserResponse;
import com.example.user.userdomain.service.SejongAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OneToOneChatService {

    private final ChatRoomService chatRoomService;
    private final SejongAuthService sejongAuthService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    /* 1대1 채팅방 생성 */
    @Transactional
    public ChatRoomResponse createChatRoomForUsers(String nickname1, String nickname2) {

        UserResponse userResponse1 = sejongAuthService.findByNickname(nickname1);
        UserResponse userResponse2 = sejongAuthService.findByNickname(nickname2);

        List<UserResponse> userResponseList = new ArrayList<>();
        userResponseList.add(userResponse1);
        userResponseList.add(userResponse2);

        String chatRoomName = userResponse1.getNickname() + "," + userResponse2.getNickname();
        ChatRoom chatRoom = chatRoomService.createChatRoom(chatRoomName);

        chatRoomService.addUserToChatRoom(userResponse1, chatRoom);
        chatRoomService.addUserToChatRoom(userResponse2, chatRoom);

        return new ChatRoomResponse(chatRoom.getId(), chatRoom.getChatRoomName(), userResponseList, chatRoom.getCreatedAt(), chatRoom.getModifiedAt());
    }

    /* 1대1 채팅방에서 메시지 보내는 로직 */
    @Transactional
    public ChatMessage sendMessageToChatRoom(ChatMessageDTO chatMessageDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDTO.getChatRoomId()).orElseThrow();
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(chatMessageDTO.getSender())
                .message(chatMessageDTO.getMessage())
                .build();

        // DB에 메시지 저장
        return chatMessageRepository.save(chatMessage);
    }

    /* 특정 채팅방의 모든 메시지 조회 */
    @Transactional
    public List<ChatMessageResponse> getMessagesFromChatRoom(Long chatRoomId) {

        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByChatRoomId(chatRoomId);

        return chatMessageList.stream()
                .map(chatMessage -> ChatMessageResponse.builder()
                        .chatRoomId(chatMessage.getId())
                        .sender(chatMessage.getSender())
                        .message(chatMessage.getMessage())
                        .createdAt(chatMessage.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
