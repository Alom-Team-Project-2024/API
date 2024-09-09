package com.example.user.userdomain.controller;

import com.example.user.userdomain.dto.UserInfoUpdateRequest;
import com.example.user.userdomain.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    /* 닉네임 중복 확인 */
    @Operation(summary = "닉네임 중복 확인", description = "닉네임을 전송하면 해당 닉네임이 사용가능한지 반환합니다. true면 사용가능, false이면 중복된 닉네임입니다.")
    @PostMapping("/duplicate/{nickname}")
    public boolean isDuplicateNickname(@PathVariable("nickname") String nickname) {
        return userProfileService.duplicateNickname(nickname);
    }

    /* 프로필 사진 확인 */
    @Operation(summary = "프로필 사진", description = "유저 프로필 사진 확인")
    @Parameter(name = "username", description = "학번")
    @GetMapping("/{username}/profile-image")
    public ResponseEntity<Resource> getProfileImage(@PathVariable("username") String username) {
        Resource file = userProfileService.getUserProfileImage(username);
        return getResourceResponseEntity(file);
    }

    /* 프로필 사진 변경 */
    @Operation(summary = "프로필 사진 변경", description = "새로운 프로필 사진으로 변경")
    @Parameter(name = "username", description = "학번")
    @PostMapping(value = "/{username}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> changeProfileImage(@PathVariable("username") String username, @RequestParam("file") MultipartFile file) {
        Resource savedFile = userProfileService.uploadProfileImage(username, file);

        return getResourceResponseEntity(savedFile);
    }

    /* 유저 온도 변경 */
    @Operation(summary = "유저 온도 변경", description = "유저 닉네임과 별 개수를 받아 온도를 조정합니다. 별 개수 1개 : -2, 별 개수 2개 : -1, 별 개수 3개 : 0, 별 개수 4개 : +1, 별 개수 5개 : +2")
    @Parameter(name = "nickname", description = "온도를 조정할 유저의 닉네임을 입력합니다.")
    @Parameter(name = "rate", description = "멘토 평가하기에서 받은 별 개수를 정수로 입력합니다.")
    @PostMapping("/rate/{nickname}/{rate}")
    public ResponseEntity<Double> changeUserPoint(@PathVariable("nickname") String nickname, @PathVariable("rate") Integer rate) {
        return ResponseEntity.ok(userProfileService.changeUserPoint(nickname, rate));
    }

    private static ResponseEntity<Resource> getResourceResponseEntity(Resource file) {
        String contentType = "application/octet-stream";

        try {
            contentType = Files.probeContentType(Paths.get(file.getFilename()));
        } catch (IOException e) {
            log.warn("파일의 Content-Type을 결정할 수 없습니다: " + e.getMessage());
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
