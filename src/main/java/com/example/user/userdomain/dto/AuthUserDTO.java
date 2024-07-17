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
    private String username;

    /* 이름 (실명) */
    private String name;

    /* 학과 */
    private String major;

    /* 학년 */
    private int studentGrade;

    /* 재학/휴학/졸업 상태 */
    private RegistrationStatus registrationStatus;
}
