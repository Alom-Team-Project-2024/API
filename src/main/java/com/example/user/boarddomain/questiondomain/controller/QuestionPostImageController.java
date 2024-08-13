package com.example.user.boarddomain.questiondomain.controller;

import com.example.user.boarddomain.questiondomain.dto.QuestionPostImageDTO;
import com.example.user.boarddomain.questiondomain.entity.QuestionPostImage;
import com.example.user.boarddomain.questiondomain.service.QuestionPostImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/question_post")
@Tag(name = "Question-Post", description = "질문 게시판 이미지 API")
public class QuestionPostImageController {

    private final QuestionPostImageService questionPostImageService;

    /* 질문 게시글 사진 등록 */
    @Operation(summary = "질문 게시판 이미지 등록", description = "질문 게시글에 이미지를 등록합니다.")
    @Parameter(name = "post_id", description = "질문 게시글 Id")
    @ApiResponse(description = "질문 글에 등록된 모든 이미지 URL을 반환합니다.")
    @PostMapping(value = "/{post_id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuestionPostImageDTO> uploadImages(@PathVariable("post_id") Long id, @RequestParam("file") List<MultipartFile> files) throws IOException {
        return questionPostImageService.saveImage(files, id);
    }

    /* 질문 게시판 이미지 전체 조회 */
    @Operation(summary = "질문 게시판 이미지 전체 조회", description = "질문글에 업로드 된 사진을 모두 조회합니다.")
    @Parameter(name = "post_id", description = "질문 게시글 Id")
    @GetMapping("/{post_id}/images")
    public List<QuestionPostImageDTO> getAllImages(@PathVariable("post_id") Long id) {
        return questionPostImageService.getAllImages(id);
    }
}