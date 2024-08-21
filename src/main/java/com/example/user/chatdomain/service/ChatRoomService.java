package com.example.user.chatdomain.service;

import com.example.user.chatdomain.dto.ChatRoomDTO;
import com.example.user.chatdomain.dto.ChatRoomResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    /* 전체 채팅방 조회 로직 */
    @Transactional
    public List<ChatRoomResponse> getAllChatRooms() {
        return chatRoomRepository.findAll().stream()
                .map(this::convertToChatRoomResponse)
                .collect(Collectors.toList());

    }

    /* id 값으로 특정 채팅방 조회 로직 */
    @Transactional
    public ChatRoomResponse getChatRoomById(Long id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow();

        return this.convertToChatRoomResponse(chatRoom);
    }

    /* 채팅방 이름으로 특정 채팅방 조회 로직 */
    @Transactional
    public ChatRoomResponse getChatRoomByName(String name) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByChatRoomName(name).orElseThrow();
        return this.convertToChatRoomResponse(chatRoom);
    }

    /* 특정 유저가 참여중인 모든 채팅방 조회 로직 */
    @Transactional
    public List<ChatRoomResponse> findRoomsByChatRoomName(String nickname) {

        return chatRoomRepository.findAllByChatRoomName(nickname).stream()
                .map(this::convertToChatRoomResponse)
                .collect(Collectors.toList());
    }

    /* 새로운 채팅방 만들기 */
    /* 추후 1대1 채팅방 service에서 해당 메서드 사용하여 채팅방 이름 설정 */
    @Transactional
    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomName(name)
                .build();

        return chatRoomRepository.save(chatRoom);
    }

    /* UserChatRoom에 새로운 유저 추가 */
    /* 추후 1대1 채팅방 service에서 해당 메서드 사용하여 채팅방에 user 넣어줌 */
    @Transactional
    public void addUserToChatRoom(UserResponse userResponse, ChatRoom chatRoom) {
        User user = userRepository.findByUsername(userResponse.getUsername());

        UserChatRoom userChatRoom = UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
        userChatRoomRepository.save(userChatRoom);
    }

    private ChatRoomResponse convertToChatRoomResponse(ChatRoom chatRoom) {

        // 채팅방에 속한 유저들을 가져오기
        List<UserResponse> userResponseList = chatRoom.getUserChatRooms().stream()
                .map(userChatRoom -> {
                    User user = userChatRoom.getUser();
                    return new UserResponse(user.getId(), user.getUsername(), user.getName(), user.getNickname(), user.getProfileImage(), user.getMajor(), user.getStudentCode(), user.getStudentGrade(), user.getRegistrationStatus(), user.getRole(), user.getPoint(), user.getCreatedAt(), user.getModifiedAt());
                })
                .collect(Collectors.toList());

        return ChatRoomResponse.builder()
                .id(chatRoom.getId())
                .chatRoomName(chatRoom.getChatRoomName())
                .userResponseList(userResponseList)
                .createdAt(chatRoom.getCreatedAt())
                .modifiedAt(chatRoom.getModifiedAt())
                .build();
    }
}
