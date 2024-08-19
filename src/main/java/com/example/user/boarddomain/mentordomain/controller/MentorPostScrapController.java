package com.example.user.boarddomain.mentordomain.controller;

import com.example.user.boarddomain.mentordomain.dto.MentorPostDTO;
import com.example.user.boarddomain.mentordomain.service.MentorPostScrapService;
import com.example.user.boarddomain.mentordomain.service.MentorPostService;
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
public class MentorPostScrapController {

    private final MentorPostScrapService mentorPostScrapService;

    /* 구인 게시글 스크랩 */
    @Operation(summary = "구인 게시글 스크랩", description = "유저 학번과 구인 게시글 Id 값을 받아 스크랩합니다. 반환값은 해당 글의 스크랩 개수 입니다.")
    @PostMapping("/mentor_post/scrap/{username}/{post_id}")
    public ResponseEntity<Integer> mentorPostScrap(@PathVariable("username") String username, @PathVariable("post_id") Long post_id) {
        Integer scrapCount = mentorPostScrapService.postScrap(username, post_id);
        return ResponseEntity.ok(scrapCount);
    }

    /* 내가 스크랩 한 구인 글 조회 */
    @Operation(summary = "내가 스크랩 한 구인 게시판 글", description = "내가 스크랩 한 구인 게시판 글을 모두 조회합니다.")
    @GetMapping("/users/mentor_post/scrap/{username}")
    public List<MentorPostDTO> getMyMentorPostList(@PathVariable("username") String username) {
        return mentorPostScrapService.getMyScrapList(username);
    }
}
