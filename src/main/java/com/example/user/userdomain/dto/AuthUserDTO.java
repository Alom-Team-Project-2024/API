package com.example.user.userdomain.dto;

import com.example.user.userdomain.entity.RegistrationStatus;
import com.example.user.userdomain.entity.Role;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Builder
@Schema(description = "로그인 JSON")
public class AuthUserDTO {
    /* 아이디 */
    @Schema(description = "학번", defaultValue = "22011315")
    private String username;

    /* 이름 (실명) */
    @Schema(description = "이름", defaultValue = "백지훈")
    private String name;

    /* 학과 */
    @Schema(description = "학과", defaultValue = "컴퓨터공학과")
    private String major;

    /* 학년 */
    @Schema(description = "학년", defaultValue = "2")
    private int studentGrade;

    /* 재학/휴학/졸업 상태 */
    @Schema(description = "재학/휴학/졸업 상태", defaultValue = "TAKEOFFSCHOOL")
    private RegistrationStatus registrationStatus;
}
