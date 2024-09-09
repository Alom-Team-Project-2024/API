package com.example.user.chatdomain.repository;

import com.example.user.chatdomain.entity.UserChatRoom;
import com.example.user.userdomain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {

    List<UserChatRoom> findAllByUser(User user);
}
