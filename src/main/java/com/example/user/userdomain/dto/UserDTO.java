package com.example.user.userdomain.dto;

import com.example.user.userdomain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {

    private String username;
    private String nickname;
    private String role;
}
