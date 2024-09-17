package com.example.user.chatdomain.dto;

import com.example.user.chatdomain.entity.ChatRoom;
import com.example.user.userdomain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserChatRoomDTO {

    private String chatRoomName;

    private String username;
}
