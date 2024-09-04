package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.ReplyDTO;
import com.example.user.boarddomain.questiondomain.dto.ReplyImageDTO;
import com.example.user.boarddomain.questiondomain.dto.ReplyResponse;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.entity.Reply;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostRepository;
import com.example.user.boarddomain.questiondomain.repository.ReplyRepository;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
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
    private final UserRepository userRepository;

    /* 답변 저장 */
    @Transactional
    public Reply saveReply(@ModelAttribute ReplyDTO replyDTO, Long post_id) {
        QuestionPost questionPost = questionPostRepository.findById(post_id).orElseThrow(NullPointerException::new);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        Reply reply = Reply.builder()
                .questionPost(questionPost)
                .username(user.getUsername())
                .text(replyDTO.getText())
                .writer(user.getNickname())
                .build();

        Reply savedReply = replyRepository.save(reply);

        // replyCount 동기화
        questionPost.synchronizedReplyCount();
        questionPostRepository.save(questionPost);

        return savedReply;
    }

    /* 특정 글 모든 답변 조회 로직 */
    @Transactional
    public List<ReplyResponse> getAllReplies(Long postId) {
        List<Reply> replies = questionPostRepository.findById(postId).orElseThrow(NullPointerException::new).getReplies();

        return replies.stream().map(this::convertToReplyResponse).collect(Collectors.toList());
    }

    /* 특정 질문 게시글에 등록된 모든 답변 최신순 조회 로직 */
    @Transactional
    public List<ReplyResponse> getAllRepliesOrderByCreatedAtDesc(Long postId) {
        List<Reply> replyList = replyRepository.findAllByQuestionPostIdOrderByCreatedAtDesc(postId);

        return replyList.stream()
                .map(this::convertToReplyResponse)
                .collect(Collectors.toList());
    }

    /* 답변 좋아요 증가 로직 */
    @Transactional
    public int increaseLikes(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow();
        reply.increaseLikes();
        return reply.getLikes();
    }

    /* 답변 좋아요 감소 로직 */
    @Transactional
    public int decreaseLikes(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow();
        reply.decreaseLikes();
        return reply.getLikes();
    }


    private ReplyResponse convertToReplyResponse(Reply reply) {
        // ReplyImage 리스트를 ReplyImageDTO 리스트로 변환
        List<ReplyImageDTO> imageDTOS = reply.getImages().stream()
                .map(image -> new ReplyImageDTO(image.getImageUrl()))
                .collect(Collectors.toList());

        return ReplyResponse.builder()
                .id(reply.getId())
                .text(reply.getText())
                .username(reply.getUsername())
                .writer(reply.getWriter())
                .likes(reply.getLikes())
                .images(imageDTOS)
                .createdAt(reply.getCreatedAt())
                .modifiedAt(reply.getModifiedAt())
                .build();
    }
}
