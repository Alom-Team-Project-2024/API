package com.example.user.chatdomain.repository;

import com.example.user.chatdomain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findChatRoomByChatRoomName(String name);

    @Query("SELECT s FROM ChatRoom s WHERE s.chatRoomName LIKE %:nickname%")
    List<ChatRoom> findAllByChatRoomName(@Param("nickname") String nickname);
}
