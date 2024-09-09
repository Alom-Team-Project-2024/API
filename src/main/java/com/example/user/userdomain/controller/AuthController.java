package com.example.user.userdomain.controller;

import com.example.user.userdomain.dto.AuthUserDTO;
import com.example.user.userdomain.dto.UserResponse;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.jwt.JWTUtil;
import com.example.user.userdomain.service.SejongAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final SejongAuthService sejongAuthService;

    /**
     * 프론트에서 API 로그인 후 서버측으로 user 전달 (아이디, 비밀번호, 이름, 학과, 학년, 재학/휴학/수료 상태)
     * /users/login post 요청시 http body에 있는 user data를 통해 token 발급
     */
    /* 로그인 */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "세종대 인증을 받은 사용자 로그인")
    public UserResponse login(@RequestBody AuthUserDTO authUserDTO, HttpServletResponse response) {
        User findUser = sejongAuthService.saveUser(authUserDTO);

        String token = jwtUtil.createAccessToken(findUser.getUsername(), findUser.getNickname(), findUser.getRole());

        response.addHeader("Authorization", "Bearer " + token);

        return new UserResponse(findUser.getId(), findUser.getUsername(), findUser.getName(), findUser.getNickname(), findUser.getProfileImage(), findUser.getMajor(), findUser.getStudentCode(), findUser.getStudentGrade(), findUser.getRegistrationStatus(), findUser.getRole(), findUser.getPoint(), findUser.getCreatedAt(), findUser.getModifiedAt());
    }

    /* Id 값으로 유저 정보 조회 */
    @Operation(summary = "Id를 통해 유저 조회", description = "유저 id값을 통해 특정 유저 정보를 조회합니다.")
    @GetMapping("/id/{id}")
    public UserResponse findUser(@PathVariable("id") Long id) {
        return sejongAuthService.findUser(id);
    }

    /* 학번으로 유저 정보 조회 */
    @Operation(summary = "학번으로 유저 조회", description = "유저 학번을 통해 유저 정보를 조회합니다.")
    @GetMapping("/username/{username}")
    public UserResponse findByUsername(@PathVariable("username") String username) {
        return sejongAuthService.findByUsername(username);
    }

    /* 닉네임으로 유저 정보 조회 */
    @Operation(summary = "닉네임으로 유저 조회", description = "유저 닉네임을 통해 유저 정보를 조회합니다.")
    @GetMapping("/nickname/{nickname}")
    public UserResponse findByNickname(@PathVariable("nickname") String nickname) {
        return sejongAuthService.findByNickname(nickname);
    }

    /* 가입된 모든 유저 조회 */
    @Operation(summary = "모든 유저 조회", description = "가입된 모든 유저를 조회합니다.")
    @GetMapping("/all")
    public List<UserResponse> findAllUsers() {
        return sejongAuthService.findAllUsers();
    }
}
