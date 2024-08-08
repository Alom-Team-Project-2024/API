package com.example.user.boarddomain.questiondomain.controller;

import com.example.user.boarddomain.questiondomain.dto.QuestionPostDTO;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostRepository;
import com.example.user.boarddomain.questiondomain.service.QuestionPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question_post")
@AllArgsConstructor
@Tag(name = "Question-Post", description = "질문게시판 API")
public class QuestionPostController {

    private final QuestionPostService questionPostService;
    private final QuestionPostRepository questionPostRepository;

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
    @Transactional
    @GetMapping("/{post_id}")
    public Optional<QuestionPost> getPost(@PathVariable("post_id") long id) {
        return questionPostRepository.findById(id);
    }


    /* 전체 글 목록 조회 */
    @Operation(summary = "전체 글 조회", description = "작성된 모든 글을 확인합니다.")
    @Transactional
    @GetMapping("")
    public List<QuestionPost> getAllPosts() {
        return questionPostRepository.findAll();
    }

    /* 작성자를 통한 글 조회 */
    @Operation(summary = "작성자 글 조회", description = "특정 유저가 작성한 모든 글을 확인합니다.")
    @Parameter(name = "username", description = "학번")
    @Transactional
    @GetMapping("username/{username}")
    public List<QuestionPost> getPostsByUsername(@PathVariable("username") String username) {
        return questionPostRepository.findAllByWriter(username);
    }

    /* 수업명을 통한 글 조회 */
    @Operation(summary = "과목 글 조회", description = "특정 과목에 해당하는 모든 글을 확인합니다.")
    @Parameter(name = "subject", description = "과목명")
    @Transactional
    @GetMapping("subject/{subject}")
    public List<QuestionPost> getPostsBySubject(@PathVariable("subject") String subject) {
        return questionPostRepository.findAllBySubject(subject);
    }
}
