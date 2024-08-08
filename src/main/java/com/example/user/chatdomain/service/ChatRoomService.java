package com.example.user.chatdomain.service;

import com.example.user.chatdomain.entity.ChatRoom;
import com.example.user.chatdomain.repository.mysql.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    /* 전체 채팅방 조회 로직 */
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    /* id 값으로 특정 채팅방 조회 로직 */
    public ChatRoom getChatRoomById(Long id) {
        return chatRoomRepository.findById(id).orElseThrow();
    }

    /* 채팅방 이름으로 특정 채팅방 조회 로직 */
    public ChatRoom getChatRoomByName(String name) {
        return chatRoomRepository.findChatRoomByName(name).orElseThrow();
    }
}
