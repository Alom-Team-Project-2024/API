package com.example.user.userdomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 닉네임 변경시 사용하는 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description=  "닉네임 변경 JSON")
public class UserInfoUpdateRequest {
    @Schema(description = "학번", defaultValue = "22011315")
    private String username;
    @Schema(description = "새로운 닉네임", defaultValue = "newNickname")
    private String nickname;
}
