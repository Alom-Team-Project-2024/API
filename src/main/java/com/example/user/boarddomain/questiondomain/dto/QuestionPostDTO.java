package com.example.user.boarddomain.questiondomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class QuestionPostDTO {
    /* 수업 명 */
    @Schema(description = "과목명 및 제목", defaultValue = "고급 C 프로그래밍 및 실습")
    private String subject;

    /* 본문 */
    @Schema(description = "본문", defaultValue = "C언어 포인터를 잘 모르겠어요 ㅠㅠ 실습문제 3 풀어주실분")
    private String text;
}
