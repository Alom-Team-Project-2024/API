package com.example.user.boarddomain.mentordomain.dto;

import com.example.user.boarddomain.mentordomain.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MentorPostResponse {

    /* 구인 게시판 글 Id */
    @Schema(description = "구인 게시판 글 Id")
    private Long id;

    /* 글 카테고리 (멘토 구해요 / 멘티 구해요) */
    @Schema(description = "글 카테고리 (멘토 구해요 / 멘티 구해요)")
    private Category category;

    /* 글 제목 */
    @Schema(description = "제목")
    private String title;

    /* 글 본문 */
    @Lob
    @Schema(description = "본문")
    private String text;

    /* 작성자 */
    @Schema(description = "작성자")
    private String writer;

    /* 학과 */
    @Schema(description = "학과")
    private String major;

    /* 좋아요 */
    @Schema(description = "좋아요")
    private int likes;

    /* 글 작성 시간 */
    @Schema(description = "글 작성 시간")
    private LocalDateTime createdAt;

    /* 글 수정 시간 */
    @Schema(description = "글 수정 시간")
    private LocalDateTime modifiedAt;
}
