package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.QuestionPostImageDTO;
import com.example.user.boarddomain.questiondomain.dto.QuestionPostResponse;
import com.example.user.boarddomain.questiondomain.dto.ReplyDTO;
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

    private QuestionPostResponse convertToResponse(QuestionPost questionPost) {
        // QuestionPostImage 리스트를 QuestionPostImageDTO 리스트로 변환
        List<QuestionPostImageDTO> imageDTOS = getQuestionPostImageDTOS(questionPost);

        // Reply 리스트를 ReplyDTO 리스트로 변환
        List<ReplyDTO> replyDTOS = getReplyDTOS(questionPost);

        // QuestionPost를 QuestionPostResponse로 변환하여 반환
        return QuestionPostResponse.builder()
                .id(questionPost.getId())
                .subject(questionPost.getSubject())
                .text(questionPost.getText())
                .writer(questionPost.getWriter())
                .likes(questionPost.getLikes())
                .scrapCount(questionPost.getScrapCount())
                .replyCount(questionPost.getReplyCount())
                .replies(replyDTOS)
                .images(imageDTOS)
                .createdAt(questionPost.getCreatedAt())
                .modifiedAt(questionPost.getModifiedAt())
                .build();
    }

    private static List<ReplyDTO> getReplyDTOS(QuestionPost questionPost) {
        return questionPost.getReplies().stream()
                .map(reply -> new ReplyDTO(reply.getText()))
                .collect(Collectors.toList());
    }

    private static List<QuestionPostImageDTO> getQuestionPostImageDTOS(QuestionPost questionPost) {
        return questionPost.getImages().stream()
                .map(image -> new QuestionPostImageDTO(image.getImageUrl()))
                .collect(Collectors.toList());
    }
}
