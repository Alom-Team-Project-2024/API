package com.example.user.chatdomain.repository;

import com.example.user.chatdomain.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    List<ChatMessage> findByChatRoomId(String chatRoomId);
}
