package com.example.user.boarddomain.questiondomain.controller;

import com.example.user.boarddomain.questiondomain.entity.ReplyImage;
import com.example.user.boarddomain.questiondomain.service.ReplyImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/question_post")
@AllArgsConstructor
@Tag(name = "reply-image-controller", description = "질문 게시글 답변 이미지 API")
public class ReplyImageController {

    private final ReplyImageService replyImageService;

    /* 답변 이미지 등록 */
    @Operation(summary = "답변 이미지 등록", description = "질문 게시글 답변 이미지를 등록합니다.")
    @Parameter(name = "post_id", description = "질문 게시글 Id")
    @PostMapping(value = "/{post_id}/reply/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReplyImage> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable("post_id") Long id) {
        try {
            ReplyImage replyImage = replyImageService.saveImage(file, id);
            return ResponseEntity.ok(replyImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    /* 답변 이미지 조회 */
    @Operation(summary = "답변 이미지 조회", description = "특정 질문 게시글에 대한 답변의 이미지를 모두 조회합니다.")
    @Parameter(name = "post_id", description = "질문 게시글 Id")
    @GetMapping("/{post_id}/reply/images")
    public List<ReplyImage> getAllImages(@PathVariable("post_id") Long id) {
        return replyImageService.getAllImages(id);
    }
}
