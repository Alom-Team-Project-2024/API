package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.ReplyDTO;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.entity.Reply;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostRepository;
import com.example.user.boarddomain.questiondomain.repository.ReplyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
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
