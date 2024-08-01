package com.example.user.boarddomain.questiondomain.dto;

import lombok.Data;

@Data
public class QuestionPostDTO {
    /* 수업 명 */
    private String subject;

    /* 본문 */
    private String text;
}
