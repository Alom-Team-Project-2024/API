package com.example.user.boarddomain.questiondomain.controller;

import com.example.user.boarddomain.questiondomain.dto.QuestionPostDTO;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostRepository;
import com.example.user.boarddomain.questiondomain.service.QuestionPostService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question_post")
@AllArgsConstructor
public class QuestionPostController {

    private final QuestionPostService questionPostService;
    private final QuestionPostRepository questionPostRepository;

    /**
     * JSON 형식으로 HTTP body에 제목, 본문 데이터가 넘어옴
     * {
     * "subject" : "글 제목",
     * "text" : "본문"
     * }
     * 서버에서는 body에 있는 데이터를 통해 게시글 작성
     * 작성자는 QuestionPostService에서 현재 세션에 등록된 사용자를 통해 확인
     */
    /* 글 작성 */
    @PostMapping("")
    public void post(@RequestBody QuestionPostDTO questionPostDTO) {
        questionPostService.savePost(questionPostDTO);
    }

    /* 특정 글 조회 */
    @GetMapping("/{post_id}")
    public Optional<QuestionPost> getPost(@PathVariable("post_id") long id) {
        return questionPostRepository.findById(id);
    }


    /* 전체 글 목록 조회 */
    @Transactional
    @GetMapping("")
    public List<QuestionPost> getAllPosts() {
        return questionPostRepository.findAll();
    }

    /* 작성자를 통한 글 조회 */
    @Transactional
    @GetMapping("username/{username}")
    public List<QuestionPost> getPostsByUsername(@PathVariable("username") String username) {
        return questionPostRepository.findAllByWriter(username);
    }

    /* 수업명을 통한 글 조회 */
    @Transactional
    @GetMapping("subject/{subject}")
    public List<QuestionPost> getPostsBySubject(@PathVariable("subject") String subject) {
        return questionPostRepository.findAllBySubject(subject);
    }
}
