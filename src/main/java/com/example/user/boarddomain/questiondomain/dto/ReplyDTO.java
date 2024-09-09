package com.example.user.boarddomain.questiondomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    /* 본문 */
    @Schema(description = "답변 내용", defaultValue = "이 문제 포인터 사용해서 풀어야돼요!! 포인터 공부 다시해보세요")
    private String text;
}
