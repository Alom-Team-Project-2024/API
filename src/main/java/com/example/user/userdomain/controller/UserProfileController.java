package com.example.user.userdomain.controller;

import com.example.user.userdomain.dto.UserInfoUpdateRequest;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import com.example.user.userdomain.service.SejongAuthService;
import com.example.user.userdomain.service.UserProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "회원 API")
public class UserProfileController {

    private final UserProfileService userProfileService;

    /* 닉네임 변경 */
    @Operation(summary = "닉네임 변경", description = "새로운 닉네임 설정")
    @PatchMapping("")
    public int editNickname(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest) {
        if (userProfileService.changeNickname(userInfoUpdateRequest)) {
            return 1;
        }
        return 0;
    }

    /* 프로필 사진 확인 */
    @Operation(summary = "프로필 사진", description = "유저 프로필 사진 확인")
    @Parameter(name = "username", description = "학번")
    @GetMapping("/{username}/profile-image")
    public ResponseEntity<String> getProfileImage(@PathVariable("username") String username) {
        String userProfileImage = userProfileService.getUserProfileImage(username);
        return ResponseEntity.ok(userProfileImage);
    }


    /* 프로필 사진 변경 */
    @Operation(summary = "프로필 사진 변경", description = "새로운 프로필 사진으로 변경")
    @Parameter(name = "username", description = "학번")
    @PostMapping(value = "/{username}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeProfileImage(@PathVariable("username") String username, @RequestParam("file") MultipartFile file) {
        String imageUrl = userProfileService.uploadProfileImage(username, file);
        return ResponseEntity.ok(imageUrl);
    }

    /* 유저 온도 변경 */
    @Operation(summary = "유저 온도 변경", description = "유저 학번과 별 개수를 받아 온도를 조정합니다. 별 개수 1개 : -2, 별 개수 2개 : -1, 별 개수 3개 : 0, 별 개수 4개 : +1, 별 개수 5개 : +2")
    @Parameter(name = "username", description = "온도를 조정할 유저의 학번을 입력합니다.")
    @Parameter(name = "rate", description = "멘토 평가하기에서 받은 별 개수를 정수로 입력합니다.")
    @PostMapping("/rate/{username}/{rate}")
    public ResponseEntity<Double> changeUserPoint(@PathVariable("username") String username, @PathVariable("rate") Integer rate) {
        return ResponseEntity.ok(userProfileService.changeUserPoint(username, rate));
    }
}
