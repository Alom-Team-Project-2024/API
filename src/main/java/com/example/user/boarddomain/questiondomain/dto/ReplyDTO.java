package com.example.user.boarddomain.questiondomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyDTO {

    /* 제목 */
    @Schema(description = "답변 제목", defaultValue = "실습문제 3 답변드립니다.")
    private String title;

    /* 본문 */
    @Schema(description = "답변 내용", defaultValue = "이 문제 포인터 사용해서 풀어야돼요!! 포인터 공부 다시해보세요")
    private String text;
}
