package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.ReplyDTO;
import com.example.user.boarddomain.questiondomain.dto.ReplyImageDTO;
import com.example.user.boarddomain.questiondomain.dto.ReplyResponse;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.entity.Reply;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostRepository;
import com.example.user.boarddomain.questiondomain.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ReplyResponse> getAllReplies(Long post_id) {
        List<Reply> replies = questionPostRepository.findById(post_id).orElseThrow(NullPointerException::new).getReplies();

        return replies.stream().map(this::convertToReplyResponse).collect(Collectors.toList());
    }


    private ReplyResponse convertToReplyResponse(Reply reply) {
        // ReplyImage 리스트를 ReplyImageDTO 리스트로 변환
        List<ReplyImageDTO> imageDTOS = reply.getImages().stream()
                .map(image -> new ReplyImageDTO(image.getImageUrl()))
                .collect(Collectors.toList());

        return ReplyResponse.builder()
                .title(reply.getTitle())
                .text(reply.getText())
                .writer(reply.getWriter())
                .likes(reply.getLikes())
                .images(imageDTOS)
                .build();
    }
}
