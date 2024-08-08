package com.example.user.chatdomain.repository.mysql;

import com.example.user.chatdomain.entity.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
}
