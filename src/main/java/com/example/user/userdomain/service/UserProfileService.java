package com.example.user.userdomain.service;

import com.example.user.userdomain.dto.UserInfoUpdateRequest;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

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

    /* 중복 닉네임 확인 로직 */
    @Transactional
    public boolean duplicateNickname(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }

    /* 프로필 변경 */
    @Transactional
    public Resource uploadProfileImage(String username, MultipartFile file) {
        User user = userRepository.findByUsername(username);

        try {
            // 파일 이름을 UUID로 대체
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String uuidFilename = UUID.randomUUID() + fileExtension;

            Path filePath = Paths.get(uploadDir, uuidFilename);

            // try-with-resources를 사용하여 파일 스트림을 자동으로 닫음
            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // 저장된 파일 경로를 사용자 엔티티에 저장
            String imageUrl = filePath.toString();
            user.changeProfileImage(imageUrl); // 원본 파일 이름도 함께 저장

            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.", e);
        }
    }

    /* 프로필 확인 */
    @Transactional
    public Resource getUserProfileImage(String username) {
        log.info("프로필 확인 서비스");
        User user = userRepository.findByUsername(username);

        try {
            String filename = user.getProfileImage(); // 저장된 파일 경로
            Path file = Paths.get(filename); // 경로에서 파일을 참조
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("파일이 존재하지 않거나 읽을 수 없습니다: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("파일 경로가 잘못되었습니다: " + e.getMessage(), e);
        }

    }

    /* 온도 증감 로직 */
    @Transactional
    public double changeUserPoint(String nickname, int rate) {
        User user = userRepository.findByNickname(nickname);

        if (rate == 1) {
            user.changePoint(user.getPoint() - 2);
        } else if (rate == 2) {
            user.changePoint(user.getPoint() - 1);
        } else if (rate == 4) {
            user.changePoint(user.getPoint() + 1);
        } else if (rate == 5) {
            user.changePoint(user.getPoint() + 2);
        }

        if (user.getPoint() > 100) {
            user.changePoint(100);
        } else if (user.getPoint() < 0) {
            user.changePoint(0);
        }

        return user.getPoint();
    }
}
