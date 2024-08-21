package com.example.user.userdomain.dto;

import com.example.user.userdomain.entity.RegistrationStatus;
import com.example.user.userdomain.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {

    /* 유저 Id */
    @Schema(description = "유저 Id")
    private Long id;

    /* 아이디 */
    @Schema(description = "아이디")
    private String username;

    /* 이름 */
    @Schema(description = "이름")
    private String name;

    /* 닉네임 */
    @Schema(description = "닉네임")
    private String nickname;

    /* 프로필 사진 */
    @Schema(description = "프로필 사진")
    private String profileImage;

    /* 전공 */
    @Schema(description = "전공")
    private String major;

    /* 학번 */
    @Schema(description = "학번")
    private int studentCode;

    /* 학년 */
    @Schema(description = "학년")
    private int studentGrade;

    /* 학생 상태 */
    @Schema(description = "재학/휴학/졸업")
    private RegistrationStatus registrationStatus;

    /* 역할 */
    @Schema(description = "유저/관리자/탈퇴회원")
    private Role role;

    /* 활동 점수 */
    @Schema(description = "활동 점수")
    private double point;

    /* 생성 날짜 */
    @Schema(description = "생성 날짜")
    private LocalDateTime createdAt;

    /* 수정 날짜 */
    @Schema(description = "수정 날짜")
    private LocalDateTime modifiedAt;

}
