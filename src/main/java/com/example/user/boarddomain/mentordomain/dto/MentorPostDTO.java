package com.example.user.boarddomain.mentordomain.dto;

import com.example.user.boarddomain.mentordomain.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MentorPostDTO {

    /* 글 제목 */
    @Schema(description = "구인 글 제목", defaultValue = "인공지능과 빅데이터 3단원 알려주실 튜터분 구합니다.")
    private String title;

    /* 글 본문 */
    @Schema(description = "구인 글 본문", defaultValue = "OOO 교수님 인지빅 3단원 잘 모르겠어요. 개념설명이랑 문제풀이 도와주실 튜터분 구합니다. 시간당 20,000원 드려요")
    private String text;

    /* 카테고리 */
    @Schema(description = "멘토 구해요 / 멘티 구해요", defaultValue = "FIND_MENTOR")
    private Category category;
}
