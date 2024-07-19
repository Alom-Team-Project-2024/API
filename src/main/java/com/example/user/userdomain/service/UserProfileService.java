package com.example.user.userdomain.service;

import com.example.user.userdomain.dto.UserInfoUpdateRequest;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

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

    /* 프로필 변경 */
    @Transactional
    public String uploadProfileImage(String username, MultipartFile file) {
        User user = userRepository.findByUsername(username);

        try {
            Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = "/static/images/" + file.getOriginalFilename();
            user.changeProfileImage(imageUrl);

            return imageUrl;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    /* 프로필 확인 */
    public String getUserProfileImage(String username) {
        log.info("프로필 확인 서비스");
        User user = userRepository.findByUsername(username);

        return user.getProfileImage();
    }
}
