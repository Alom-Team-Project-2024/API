package com.example.user.chatdomain.service;

import com.example.user.chatdomain.dto.ChatRoomDTO;
import com.example.user.chatdomain.entity.ChatRoom;
import com.example.user.chatdomain.entity.UserChatRoom;
import com.example.user.chatdomain.repository.ChatRoomRepository;
import com.example.user.chatdomain.repository.UserChatRoomRepository;
import com.example.user.userdomain.dto.UserResponse;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    /* 전체 채팅방 조회 로직 */
    public List<ChatRoomDTO> getAllChatRooms() {
        return chatRoomRepository.findAll().stream()
                .map(chatRoom -> new ChatRoomDTO(chatRoom.getChatRoomName()))
                .collect(Collectors.toList());
    }

    /* id 값으로 특정 채팅방 조회 로직 */
    public ChatRoomDTO getChatRoomById(Long id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow();
        return new ChatRoomDTO(chatRoom.getChatRoomName());

    }

    /* 채팅방 이름으로 특정 채팅방 조회 로직 */
    public ChatRoomDTO getChatRoomByName(String name) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByChatRoomName(name).orElseThrow();
        return new ChatRoomDTO(chatRoom.getChatRoomName());
    }

    /* 특정 유저가 참여중인 모든 채팅방 조회 로직 */
    public List<ChatRoomDTO> findRoomsByChatRoomName(String nickname) {

        return chatRoomRepository.findAllByChatRoomName(nickname).stream()
                .map(chatRoom -> new ChatRoomDTO(chatRoom.getChatRoomName()))
                .collect(Collectors.toList());
    }

    /* 새로운 채팅방 만들기 */
    /* 추후 1대1 채팅방 service에서 해당 메서드 사용하여 채팅방 이름 설정 */
    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomName(name)
                .build();

        return chatRoomRepository.save(chatRoom);
    }

    /* UserChatRoom에 새로운 유저 추가 */
    /* 추후 1대1 채팅방 service에서 해당 메서드 사용하여 채팅방에 user 넣어줌 */
    public void addUserToChatRoom(UserResponse userResponse, ChatRoom chatRoom) {
        User user = userRepository.findByUsername(userResponse.getUsername());

        UserChatRoom userChatRoom = UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
        userChatRoomRepository.save(userChatRoom);
    }
}
