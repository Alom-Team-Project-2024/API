package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.ReplyDTO;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.entity.Reply;
import com.example.user.boarddomain.questiondomain.entity.ReplyImage;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostRepository;
import com.example.user.boarddomain.questiondomain.repository.ReplyRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyService {

    private final QuestionPostRepository questionPostRepository;
    private final ReplyRepository replyRepository;

    /* 답변 저장 */
    @Transactional
    public Reply saveReply(@ModelAttribute ReplyDTO replyDTO, Long post_id) {
        QuestionPost questionPost = questionPostRepository.findById(post_id).orElseThrow(NullPointerException::new);

        Reply reply = Reply.builder()
                .questionPost(questionPost)
                .title(replyDTO.getTitle())
                .text(replyDTO.getText())
                .writer(SecurityContextHolder.getContext().getAuthentication().getName())
                .build();

        return replyRepository.save(reply);
    }

    /* 특정 글 모든 답변 return */
    @Transactional
    public List<Reply> getAllReplies(Long post_id) {
        QuestionPost questionPost = questionPostRepository.findById(post_id).orElseThrow(NullPointerException::new);
        return questionPost.getReplies();
    }
}
