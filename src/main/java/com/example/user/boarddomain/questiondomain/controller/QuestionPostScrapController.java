package com.example.user.boarddomain.questiondomain.controller;

import com.example.user.boarddomain.questiondomain.dto.QuestionPostDTO;
import com.example.user.boarddomain.questiondomain.service.QuestionPostScrapService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionPostScrapController {

    private final QuestionPostScrapService questionPostScrapService;

    /* 질문 게시글 스크랩 */
    @Operation(summary = "질문 게시글 스크랩", description = "유저 학번과 질문 게시글 Id 값을 받아 스크랩합니다. 반환값은 해당 글의 스크랩 개수 입니다.")
    @PostMapping("/question_post/scrap/{username}/{post_id}")
    public ResponseEntity<Integer> questionPostScrap(@PathVariable("username") String username, @PathVariable("post_id") Long post_id) {
        Integer scrapCount = questionPostScrapService.postScrap(username, post_id);
        return ResponseEntity.ok(scrapCount);
    }

    /* 내가 스크랩 한 글 조회 */
    @Operation(summary = "내가 스크랩 한 질문 게시판 글", description = "내가 스크랩 한 질문 게시판 글을 모두 조회합니다.")
    @GetMapping("/users/question_post/scrap/{username}")
    public List<QuestionPostDTO> getMyQuestionPostList(@PathVariable("username") String username) {
        return questionPostScrapService.getMyScrapList(username);
    }
}
