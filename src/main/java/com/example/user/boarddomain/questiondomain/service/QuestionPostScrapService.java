package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.QuestionPostDTO;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.entity.QuestionPostScrap;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostRepository;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostScrapRepository;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionPostScrapService {

    private final UserRepository userRepository;
    private final QuestionPostRepository questionPostRepository;
    private final QuestionPostScrapRepository questionPostScrapRepository;

    /* 질문게시글 스크랩 등록 로직 */
    @Transactional
    public Integer postScrap(String username, Long post_id) {
        User user = userRepository.findByUsername(username);
        QuestionPost questionPost = questionPostRepository.findById(post_id).orElseThrow();

        questionPostScrapRepository.save(QuestionPostScrap.builder()
                .user(user)
                .questionPost(questionPost)
                .build());

        questionPost.increaseScrapCount();

        return questionPost.getScrapCount();
    }

    /* 내가 스크랩 한 질문 글 조회 로직 */
    @Transactional
    public List<QuestionPostDTO> getMyScrapList(String username) {
        User user = userRepository.findByUsername(username);
        List<QuestionPost> questionPostList = questionPostScrapRepository.findAllByUser(user);

        return questionPostList.stream()
                .map(questionPost -> new QuestionPostDTO(questionPost.getSubject(), questionPost.getText()))
                .collect(Collectors.toList());
    }
}
