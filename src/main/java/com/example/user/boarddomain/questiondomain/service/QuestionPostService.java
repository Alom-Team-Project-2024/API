package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.QuestionPostDTO;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.entity.QuestionPostImage;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostImageRepository;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostRepository;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionPostService {

    private final QuestionPostRepository questionPostRepository;
    private final UserRepository userRepository;

    /* 게시물 등록 */
    @Transactional
    public void savePost(QuestionPostDTO questionPostDTO) {
        // 세션에서 현재 사용자 확인
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        log.info("현재 사용자는 " + username + " 입니다.");

        QuestionPost questionPost = QuestionPost.builder()
                .user(user)
                .subject(questionPostDTO.getSubject())
                .text(questionPostDTO.getText())
                .writer(username)
                .build();

        questionPostRepository.save(questionPost);
    }

}
