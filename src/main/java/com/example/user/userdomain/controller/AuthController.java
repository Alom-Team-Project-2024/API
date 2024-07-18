package com.example.user.userdomain.controller;

import com.example.user.userdomain.dto.AuthUserDTO;
import com.example.user.userdomain.dto.JWTResponse;
import com.example.user.userdomain.dto.UserInfoUpdateRequest;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.jwt.JWTUtil;
import com.example.user.userdomain.repository.UserRepository;
import com.example.user.userdomain.service.SejongAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final SejongAuthService sejongAuthService;

    public AuthController(JWTUtil jwtUtil, SejongAuthService sejongAuthService) {
        this.jwtUtil = jwtUtil;
        this.sejongAuthService = sejongAuthService;
    }

    /**
     * 프론트에서 API 로그인 후 서버측으로 user 전달 (아이디, 비밀번호, 이름, 학과, 학년, 재학/휴학/수료 상태)
     * /users/login post 요청시 http body에 있는 user data를 통해 token 발급
     */
    @PostMapping("/login")
    public void login(@RequestBody AuthUserDTO authUserDTO, HttpServletResponse response) {
        User findUser = sejongAuthService.saveUser(authUserDTO);

        String token = jwtUtil.createAccessToken(findUser.getUsername(), findUser.getNickname(), findUser.getRole());

        response.addHeader("Authorization", "Bearer " + token);
    }

    /* 닉네임 변경 */
    @PatchMapping("")
    public void editNickname(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest) {
        sejongAuthService.changeNickname(userInfoUpdateRequest);
    }
}
