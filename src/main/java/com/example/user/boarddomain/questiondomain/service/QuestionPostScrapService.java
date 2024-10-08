package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.*;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.entity.QuestionPostScrap;
import com.example.user.boarddomain.questiondomain.entity.Reply;
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
    public List<QuestionPostResponse> getMyScrapList(String username) {
        User user = userRepository.findByUsername(username);
        List<QuestionPostScrap> allByUser = questionPostScrapRepository.findAllByUser(user);

        List<QuestionPost> questionPostList = allByUser.stream()
                .map(QuestionPostScrap::getQuestionPost)
                .toList();

        return questionPostList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /* 내가 스크랩 한 질문 글 최신순 조회 로직 */
    @Transactional
    public List<QuestionPostResponse> getMyScrapListOrderByDesc(String username) {
        User user = userRepository.findByUsername(username);
        List<QuestionPostScrap> questionPostScrapList = questionPostScrapRepository.findAllByUserOrderByQuestionPostCreatedAtDesc(user);

        List<QuestionPost> questionPostList = questionPostScrapList.stream()
                .map(QuestionPostScrap::getQuestionPost)
                .toList();

        return questionPostList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private QuestionPostResponse convertToResponse(QuestionPost questionPost) {
        // QuestionPostImage 리스트를 QuestionPostImageDTO 리스트로 변환
        List<QuestionPostImageDTO> imageDTOS = getQuestionPostImageDTOS(questionPost);

        // Reply 리스트를 ReplyResponse 리스트로 변환
        List<ReplyResponse> replyResponseList = getReplyResponseList(questionPost);

        // QuestionPost를 QuestionPostResponse로 변환하여 반환
        return QuestionPostResponse.builder()
                .id(questionPost.getId())
                .subject(questionPost.getSubject())
                .text(questionPost.getText())
                .username(questionPost.getUser().getUsername())
                .writer(questionPost.getWriter())
                .likes(questionPost.getLikes())
                .scrapCount(questionPost.getScrapCount())
                .replyCount(questionPost.getReplyCount())
                .replies(replyResponseList)
                .images(imageDTOS)
                .createdAt(questionPost.getCreatedAt())
                .modifiedAt(questionPost.getModifiedAt())
                .build();
    }

    private static List<ReplyResponse> getReplyResponseList(QuestionPost questionPost) {
        return questionPost.getReplies().stream()
                .map(reply -> new ReplyResponse(reply.getId(), reply.getText(), reply.getUsername(), reply.getWriter(), reply.getLikes(), getReplyImageDTOS(reply), reply.getCreatedAt(), reply.getModifiedAt()))
                .collect(Collectors.toList());
    }

    private static List<QuestionPostImageDTO> getQuestionPostImageDTOS(QuestionPost questionPost) {
        return questionPost.getImages().stream()
                .map(image -> new QuestionPostImageDTO(image.getImageUrl()))
                .collect(Collectors.toList());
    }

    private static List<ReplyImageDTO> getReplyImageDTOS(Reply reply) {
        return reply.getImages().stream()
                .map(replyImage -> new ReplyImageDTO(replyImage.getImageUrl()))
                .collect(Collectors.toList());
    }
}
