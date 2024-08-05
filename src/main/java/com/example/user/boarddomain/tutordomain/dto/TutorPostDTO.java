package com.example.user.boarddomain.tutordomain.dto;

import lombok.Data;

@Data
public class TutorPostDTO {

    /* 글 제목 */
    private String title;

    /* 글 본문 */
    private String text;

    /* 과목 명 */
    private String subject;
}
