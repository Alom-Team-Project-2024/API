package com.example.user.boarddomain.questiondomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReplyImageDTO {

    /* 답변 이미지 url */
    @Schema(description = "답변 이미지 url")
    private String imageUrl;
}
