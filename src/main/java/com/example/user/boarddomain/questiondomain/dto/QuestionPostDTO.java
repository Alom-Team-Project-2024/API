package com.example.user.boarddomain.questiondomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class QuestionPostDTO {

    /* 수업 명 */
    @Schema(description = "과목명 및 제목", defaultValue = "고급C프로그래밍및실습")
    private String subject;

    /* 본문 */
    @Lob
    @Schema(description = "본문", defaultValue = "C언어 포인터를 잘 모르겠어요 ㅠㅠ 실습문제 3 풀어주실분")
    private String text;
}
