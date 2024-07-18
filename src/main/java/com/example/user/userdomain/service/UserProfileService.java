package com.example.user.userdomain.service;

import com.example.user.userdomain.dto.UserInfoUpdateRequest;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserProfileService {

    private final UserRepository userRepository;

    public UserProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* 닉네임 변경 */
    @Transactional
    public boolean changeNickname(UserInfoUpdateRequest userInfoUpdateRequest) {
        User user = userRepository.findByUsername(userInfoUpdateRequest.getUsername());
        String newNickname = userInfoUpdateRequest.getNickname();

        if (userRepository.existsByNickname(newNickname)) {
            log.info("이미 사용중인 닉네임입니다.");
            return false;
        }
        log.info("현재 user는 " + user.getUsername() + " 입니다.");
        log.info("닉네임이 " + newNickname + " 으로 변경되었습니다.");
        user.changeNickname(newNickname);

        log.info("현재 user의 닉네임은 " + userRepository.findByUsername("22011315").getNickname() + " 입니다.");
        return true;

    }
}
