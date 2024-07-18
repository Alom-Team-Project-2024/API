package com.example.user.userdomain.dto;

import com.example.user.userdomain.entity.RegistrationStatus;
import com.example.user.userdomain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 DTO
 */

@Getter
@Setter
@AllArgsConstructor
public class AuthUserDTO {
    /* 아이디 */
    private String username; // 22011315

    /* 이름 (실명) */
    private String name; // 백지훈

    /* 학과 */
    private String major; // 컴퓨터공학과

    /* 학년 */
    private int studentGrade; // 2

    /* 재학/휴학/졸업 상태 */
    private RegistrationStatus registrationStatus; //
}
