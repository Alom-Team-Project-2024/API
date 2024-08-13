package com.example.user.boarddomain.questiondomain.dto;

import com.example.user.boarddomain.questiondomain.entity.ReplyImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ReplyResponse {

    /* 제목 */
    @Schema(description = "답변 제목")
    private String title;

    /* 본문 */
    @Schema(description = "답변 본문")
    private String text;

    /* 작성자 */
    @Schema(description = "답변 작성자")
    private String writer;

    /* 좋아요 */
    @Schema(description = "답변 좋아요")
    private int likes;

    /* 답변 이미지 */
    @Schema(description = "답변 이미지")
    private List<ReplyImageDTO> images;
}
