package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.QuestionPostDTO;
import com.example.user.boarddomain.questiondomain.dto.QuestionPostImageDTO;
import com.example.user.boarddomain.questiondomain.dto.QuestionPostResponse;
import com.example.user.boarddomain.questiondomain.dto.ReplyDTO;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostRepository;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionPostService {

    private final QuestionPostRepository questionPostRepository;
    private final UserRepository userRepository;

    /* 게시물 등록 */
    @Transactional
    public QuestionPost savePost(QuestionPostDTO questionPostDTO) {
        // 세션에서 현재 사용자 확인
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        QuestionPost questionPost = QuestionPost.builder()
                .user(user)
                .subject(questionPostDTO.getSubject())
                .text(questionPostDTO.getText())
                .writer(username)
                .build();

        return questionPostRepository.save(questionPost);
    }

    /* 글 Id 값으로 특정 글 조회 로직 */
    @Transactional
    public QuestionPostResponse findPost(Long id) {
        QuestionPost questionPost = questionPostRepository.findById(id).orElseThrow();

        // QuestionPostImage 리스트를 QuestionPostImageDTO 리스트로 변경
        List<QuestionPostImageDTO> imageDTOS = getQuestionPostImageDTOS(questionPost);

        List<ReplyDTO> replyDTOS = getReplyDTOS(questionPost);

        QuestionPostResponse questionPostResponse = QuestionPostResponse.builder()
                .subject(questionPost.getSubject())
                .text(questionPost.getText())
                .writer(questionPost.getWriter())
                .likes(questionPost.getLikes())
                .clips(questionPost.getClips())
                .replyCount(questionPost.getReplyCount())
                .replies(replyDTOS)
                .images(imageDTOS)
                .build();

        return questionPostResponse;
    }

    /* 질문게시판에 등록된 모든 글 조회 로직 */
    @Transactional
    public List<QuestionPostResponse> findAllPosts() {

        List<QuestionPost> questionPosts = questionPostRepository.findAll();

        // 조회한 QuestionPost 엔티티 리스트를 QuestionPostResponse DTO 리스트로 변환
        return questionPosts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /* 특정 작성자가 작성한 질문 글 조회 로직 */
    @Transactional
    public List<QuestionPostResponse> findPostsByUsername(String username) {
        List<QuestionPost> questionPosts = questionPostRepository.findAllByWriter(username);

        // 조회한 QuestionPost 엔티티 리스트를 QuestionPostResponse DTO 리스트로 변환
        return questionPosts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /* 특정 수업명을 통한 모든 질문 글 조회 로직 */
    @Transactional
    public List<QuestionPostResponse> findPostsBySubject(String subject) {
        List<QuestionPost> questionPosts = questionPostRepository.findAllByWriter(subject);

        // 조회한 QuestionPost 엔티티 리스트를 QuestionPostResponse DTO 리스트로 변환
        return questionPosts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /* 질문 게시글 좋아요 증가 로직 */
    @Transactional
    public int increaseLikes(Long id) {
        QuestionPost questionPost = questionPostRepository.findById(id).orElseThrow();
        questionPost.increaseLikes();
        return questionPost.getLikes();
    }

    /* 질문 게시글 좋아요 감소 로직 */
    @Transactional
    public int decreaseLikes(Long id) {
        QuestionPost questionPost = questionPostRepository.findById(id).orElseThrow();
        questionPost.decreaseLikes();
        return questionPost.getLikes();
    }


    private QuestionPostResponse convertToResponse(QuestionPost questionPost) {
        // QuestionPostImage 리스트를 QuestionPostImageDTO 리스트로 변환
        List<QuestionPostImageDTO> imageDTOS = getQuestionPostImageDTOS(questionPost);

        // Reply 리스트를 ReplyDTO 리스트로 변환
        List<ReplyDTO> replyDTOS = getReplyDTOS(questionPost);

        // QuestionPost를 QuestionPostResponse로 변환하여 반환
        return QuestionPostResponse.builder()
                .subject(questionPost.getSubject())
                .text(questionPost.getText())
                .writer(questionPost.getWriter())
                .likes(questionPost.getLikes())
                .clips(questionPost.getClips())
                .replyCount(questionPost.getReplyCount())
                .replies(replyDTOS)
                .images(imageDTOS)
                .build();
    }

    private static List<ReplyDTO> getReplyDTOS(QuestionPost questionPost) {
        return questionPost.getReplies().stream()
                .map(reply -> new ReplyDTO(reply.getTitle(), reply.getText()))
                .collect(Collectors.toList());
    }

    private static List<QuestionPostImageDTO> getQuestionPostImageDTOS(QuestionPost questionPost) {
        return questionPost.getImages().stream()
                .map(image -> new QuestionPostImageDTO(image.getImageUrl()))
                .collect(Collectors.toList());
    }

}