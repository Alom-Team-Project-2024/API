package com.example.user.boarddomain.questiondomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class QuestionPostResponse {

    /* 글 Id */
    @Schema(description = "글 Id")
    private Long id;

    /* 제목 (과목명) */
    @Schema(description = "글 제목(과목명)")
    private String subject;

    /* 본문 내용 */
    @Lob
    @Schema(description = "본문 내용")
    private String text;

    /* 작성자 */
    @Schema(description = "작성자")
    private String writer;

    /* 좋아요 */
    @Schema(description = "좋아요")
    private int likes;

    /* 스크랩 수 */
    @Schema(description = "스크랩 수")
    private int scrapCount;

    /* 답변 수 */
    @Schema(description = "답변 수")
    private int replyCount;

    /* 글 답변 */
    @Schema(description = "등록된 답변")
    private List<ReplyDTO> replies;

    /* 첨부된 이미지 */
    @Schema(description = "첨부된 이미지")
    private List<QuestionPostImageDTO> images;

    /* 글 작성 시간 */
    @Schema(description = "글 작성 시간")
    private LocalDateTime createdAt;

    /* 글 수정 시간 */
    @Schema(description = "글 수정 시간")
    private LocalDateTime modifiedAt;
}
