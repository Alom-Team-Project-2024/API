package com.example.user.userdomain.service;

import com.example.user.userdomain.dto.AuthUserDTO;
import com.example.user.userdomain.dto.UserInfoUpdateRequest;
import com.example.user.userdomain.entity.Role;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 프론트에서 세종대 인증 API를 통해 로그인 구현 코드 작성
 * 로그인 후 사용자 ID, 이름, 학과, 학년, 재학/휴학 상태를 HTTP Body에 담아 서버측으로 전달
 * 서버에서는 받은 데이터를 DB에 저장
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SejongAuthService {

    private final UserRepository userRepository;

    /* 프론트에서 넘어온 유저 데이터 DB에 저장 */
    @Transactional
    public User saveUser(AuthUserDTO authUserDTO) {
        /* DB에 저장된 회원이 아닌경우 */
        if (!userRepository.existsByUsername(authUserDTO.getUsername())) {
            User user = User.builder()
                    .username(authUserDTO.getUsername())
                    .name(authUserDTO.getName())
                    .nickname("user" + (int) (Math.random() * 1000))
                    .major(authUserDTO.getMajor())
                    .studentGrade(authUserDTO.getStudentGrade())
                    .studentCode(Integer.parseInt(authUserDTO.getUsername().substring(0, 2)))
                    .registrationStatus(authUserDTO.getRegistrationStatus())
                    .role(Role.USER)
                    .point(36.5)
                    .build();
            userRepository.save(user);

            return user;
        }
        return userRepository.findByUsername(authUserDTO.getUsername());
    }


}
