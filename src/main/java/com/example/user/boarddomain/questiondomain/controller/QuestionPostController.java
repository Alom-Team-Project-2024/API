package com.example.user.boarddomain.questiondomain.controller;

import com.example.user.boarddomain.questiondomain.dto.QuestionPostDTO;
import com.example.user.boarddomain.questiondomain.dto.QuestionPostResponse;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.service.QuestionPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question_post")
@AllArgsConstructor
@Tag(name = "Question-Post", description = "질문게시판 API")
public class QuestionPostController {

    private final QuestionPostService questionPostService;

    /* 글 작성 */
    @Operation(summary = "질문 게시판 글 등록", description = "과목명 (subject), 본문 (text)를 작성하여 질문 게시판에 새로운 글을 등록합니다. 작성 글의 id 값을 반환합니다.")
    @PostMapping("")
    public ResponseEntity<Long> post(@RequestBody QuestionPostDTO questionPostDTO) {

        QuestionPost questionPost = questionPostService.savePost(questionPostDTO);

        return ResponseEntity.ok(questionPost.getId());
    }

    /* 특정 글 조회 */
    @Operation(summary = "글 조회", description = "post_id를 통해 작성된 글을 확인합니다.")
    @Parameter(name = "post_id", description = "질문 게시글 Id 값")
    @GetMapping("/{post_id}")
    public QuestionPostResponse getPost(@PathVariable("post_id") long id) {
        return questionPostService.findPost(id);
    }


    /* 전체 글 목록 조회 */
    @Operation(summary = "전체 글 조회", description = "작성된 모든 글을 확인합니다.")
    @GetMapping("")
    public List<QuestionPostResponse> getAllPosts() {
        return questionPostService.findAllPosts();
    }

    /* 작성자를 통한 글 조회 */
    @Operation(summary = "작성자 글 조회", description = "특정 유저가 작성한 모든 글을 확인합니다.")
    @Parameter(name = "username", description = "학번")
    @GetMapping("username/{username}")
    public List<QuestionPostResponse> getPostsByUsername(@PathVariable("username") String username) {
        return questionPostService.findPostsByUsername(username);
    }

    /* 수업명을 통한 글 조회 */
    @Operation(summary = "과목 글 조회", description = "특정 과목에 해당하는 모든 글을 확인합니다.")
    @Parameter(name = "subject", description = "과목명")
    @GetMapping("subject/{subject}")
    public List<QuestionPostResponse> getPostsBySubject(@PathVariable("subject") String subject) {
        return questionPostService.findPostsBySubject(subject);
    }

    /* 게시글 좋아요 증가 */
    @Operation(summary = "게시글 좋아요 증가", description = "게시글의 Id 값을 전달하면 해당 게시글의 좋아요 수가 1 증가합니다.")
    @Parameter(name = "post_id", description = "해당 게시물 id")
    @PostMapping("/{post_id}/likes/up")
    public ResponseEntity<Integer> increaseLikes(@PathVariable("post_id") Long id) {
        return ResponseEntity.ok(questionPostService.increaseLikes(id));
    }

    /* 게시글 좋아요 감소 */
    @Operation(summary = "게시글 좋아요 감소", description = "게시글의 Id 값을 전달하면 해당 게시글의 좋아요 수가 1 감소합니다. 단, 좋아요 수가 0인 게시물의 경우 변동이 없습니다.")
    @Parameter(name = "post_id", description = "해당 게시물 id")
    @PostMapping("/{post_id}/likes/down")
    public ResponseEntity<Integer> decreaseLikes(@PathVariable("post_id") Long id) {
        return ResponseEntity.ok(questionPostService.decreaseLikes(id));
    }
}
