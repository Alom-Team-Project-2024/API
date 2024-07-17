package com.example.user.userdomain.controller;

import com.example.user.userdomain.dto.AuthUserDTO;
import com.example.user.userdomain.dto.JWTResponse;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.jwt.JWTUtil;
import com.example.user.userdomain.repository.UserRepository;
import com.example.user.userdomain.service.SejongAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> login(@RequestBody AuthUserDTO authUserDTO) {
        User findUser = sejongAuthService.saveUser(authUserDTO);

        String token = jwtUtil.createAccessToken(findUser.getUsername(), findUser.getRole());

        return ResponseEntity.ok(new JWTResponse(token));
    }
}
