package com.example.user.userdomain.controller;

import com.example.user.userdomain.dto.UserInfoUpdateRequest;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import com.example.user.userdomain.service.UserProfileService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserProfileController {

    private final UserRepository userRepository;
    private final UserProfileService userProfileService;

    public UserProfileController(UserRepository userRepository, UserProfileService userProfileService) {
        this.userRepository = userRepository;
        this.userProfileService = userProfileService;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    /* 닉네임 변경 */
    @PatchMapping("")
    public String editNickname(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest) {
        if (userProfileService.changeNickname(userInfoUpdateRequest)) {
            return "닉네임이 " + userInfoUpdateRequest.getNickname() + " 으로 변경되었습니다.";
        }
        return "이미 존재하는 닉네임입니다.";
    }
}
