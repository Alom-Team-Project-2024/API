package com.example.user.chatdomain.repository;

import com.example.user.chatdomain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findChatRoomByChatRoomName(String name);
}
