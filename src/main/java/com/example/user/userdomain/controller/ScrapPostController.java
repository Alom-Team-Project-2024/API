package com.example.user.userdomain.controller;

import com.example.user.boarddomain.mentordomain.dto.MentorPostResponse;
import com.example.user.boarddomain.mentordomain.service.MentorPostScrapService;
import com.example.user.boarddomain.questiondomain.dto.QuestionPostResponse;
import com.example.user.boarddomain.questiondomain.service.QuestionPostScrapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User")
public class ScrapPostController {

    private final QuestionPostScrapService questionPostScrapService;
    private final MentorPostScrapService mentorPostScrapService;

    /* 내가 스크랩 한 질문 게시판 글 조회 */
    @Operation(summary = "내가 스크랩 한 질문 게시판 글", description = "내가 스크랩 한 질문 게시판 글을 모두 조회합니다.")
    @GetMapping("/users/question_post/scrap/{username}")
    public List<QuestionPostResponse> getMyQuestionPostList(@PathVariable("username") String username) {
        return questionPostScrapService.getMyScrapList(username);
    }

    /* 내가 스크랩 한 구인 글 조회 */
    @Operation(summary = "내가 스크랩 한 구인 게시판 글", description = "내가 스크랩 한 구인 게시판 글을 모두 조회합니다.")
    @GetMapping("/users/mentor_post/scrap/{username}")
    public List<MentorPostResponse> getMyMentorPostList(@PathVariable("username") String username) {
        return mentorPostScrapService.getMyScrapList(username);
    }
}
