package com.example.user.boarddomain.questiondomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class QuestionPostResponse {

    /* 제목 (과목명) */
    @Schema(description = "글 제목(과목명)")
    private String subject;

    /* 본문 내용 */
    @Schema(description = "본문 내용")
    private String text;

    /* 첨부된 이미지 */
    @Schema(description = "첨부된 이미지")
    private List<MultipartFile> images;
}
