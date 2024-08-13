package com.example.user.boarddomain.mentordomain.service;

import com.example.user.boarddomain.mentordomain.dto.MentorPostDTO;
import com.example.user.boarddomain.mentordomain.dto.MentorPostResponse;
import com.example.user.boarddomain.mentordomain.entity.Category;
import com.example.user.boarddomain.mentordomain.entity.MentorPost;
import com.example.user.boarddomain.mentordomain.repository.MentorPostRepository;
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
public class MentorPostService {

    private final MentorPostRepository mentorPostRepository;
    private final UserRepository userRepository;

    /* 구인 게시판 글 등록 로직 */
    @Transactional
    public MentorPostResponse savePost(MentorPostDTO mentorPostDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        MentorPost mentorPost = MentorPost.builder()
                .user(user)
                .category(mentorPostDTO.getCategory())
                .title(mentorPostDTO.getTitle())
                .text(mentorPostDTO.getText())
                .writer(username)
                .major(user.getMajor())
                .build();

        mentorPostRepository.save(mentorPost);

        return MentorPostResponse.builder()
                .category(mentorPost.getCategory())
                .title(mentorPostDTO.getTitle())
                .text(mentorPostDTO.getText())
                .writer(mentorPost.getWriter())
                .major(mentorPost.getMajor())
                .likes(mentorPost.getLikes())
                .build();
    }

    /* 구인 게시판 전체 글 조회 로직(카테고리 상관 X) */
    @Transactional
    public List<MentorPostResponse> findAllPosts() {
        List<MentorPost> mentorPosts = mentorPostRepository.findAll();

        return mentorPosts.stream()
                .map(mentorPost -> MentorPostResponse.builder()
                        .category(mentorPost.getCategory())
                        .title(mentorPost.getTitle())
                        .text(mentorPost.getText())
                        .writer(mentorPost.getWriter())
                        .major(mentorPost.getMajor())
                        .likes(mentorPost.getLikes())
                        .build())
                .collect(Collectors.toList());
    }

    /* 구인 게시판 작성자를 통한 글 조회 로직 */
    @Transactional
    public List<MentorPostResponse> findPostsByWriter(String username) {
        List<MentorPost> mentorPosts = mentorPostRepository.findMentorPostsByWriter(username);

        return mentorPosts.stream()
                .map(mentorPost -> MentorPostResponse.builder()
                        .category(mentorPost.getCategory())
                        .title(mentorPost.getTitle())
                        .text(mentorPost.getText())
                        .writer(mentorPost.getWriter())
                        .major(mentorPost.getMajor())
                        .likes(mentorPost.getLikes())
                        .build())
                .collect(Collectors.toList());
    }

    /* 게시글 고유 id를 통한 특정 글 조회 로직 */
    @Transactional
    public MentorPostResponse findPostById(Long id) {
        MentorPost mentorPost = mentorPostRepository.findById(id).orElseThrow();
        return new MentorPostResponse(mentorPost.getCategory(), mentorPost.getTitle(), mentorPost.getText(), mentorPost.getWriter(), mentorPost.getMajor(), mentorPost.getLikes());
    }

    /* 게시글 제목을 통한 특정 글 조회 로직 */
    @Transactional
    public List<MentorPostResponse> findPostByTitle(String title) {
        return mentorPostRepository.findMentorPostsByTitle(title).stream()
                .map(mentorPost -> MentorPostResponse.builder()
                        .category(mentorPost.getCategory())
                        .title(mentorPost.getTitle())
                        .text(mentorPost.getText())
                        .writer(mentorPost.getWriter())
                        .major(mentorPost.getMajor())
                        .likes(mentorPost.getLikes())
                        .build())
                .collect(Collectors.toList());
    }

    /* 카테고리를 통한 글 조회 로직 */
    @Transactional
    public List<MentorPostResponse> findPostsByCategory(Category category) {
        return mentorPostRepository.findMentorPostsByCategory(category).stream()
                .map(mentorPost -> MentorPostResponse.builder()
                        .category(mentorPost.getCategory())
                        .title(mentorPost.getTitle())
                        .text(mentorPost.getText())
                        .writer(mentorPost.getWriter())
                        .major(mentorPost.getMajor())
                        .likes(mentorPost.getLikes())
                        .build())
                .collect(Collectors.toList());
    }

    /* 글 삭제 */
    @Transactional
    public void deletePost(Long id) {
        mentorPostRepository.deleteById(id);
    }
}
