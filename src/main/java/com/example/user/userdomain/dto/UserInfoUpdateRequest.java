package com.example.user.userdomain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 닉네임 변경시 사용하는 DTO
 */
@Getter
@AllArgsConstructor
public class UserInfoUpdateRequest {
    private String username;
    private String nickname;
}
