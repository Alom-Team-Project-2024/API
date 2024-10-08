package com.example.user.boarddomain.questiondomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ReplyResponse {

    /* 답변 Id*/
    @Schema(description = "답변 Id")
    private Long id;

    /* 본문 */
    @Schema(description = "답변 본문")
    private String text;

    /* 학번 */
    @Schema(description = "학번")
    private String username;

    /* 작성자 */
    @Schema(description = "답변 작성자")
    private String writer;

    /* 좋아요 */
    @Schema(description = "답변 좋아요")
    private int likes;

    /* 답변 이미지 */
    @Schema(description = "답변 이미지")
    private List<ReplyImageDTO> images;

    /* 답변 작성 시간 */
    @Schema(description = "답변 작성 시간")
    private LocalDateTime createdAt;

    /* 답변 수정 시간 */
    @Schema(description = "답변 수정 시간")
    private LocalDateTime modifiedAt;
}
