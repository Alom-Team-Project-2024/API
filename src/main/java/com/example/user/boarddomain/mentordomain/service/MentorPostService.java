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
                .writer(user.getNickname())
                .major(user.getMajor())
                .build();

        mentorPostRepository.save(mentorPost);

        return this.convertToMentorPostResponse(mentorPost);
    }

    /* 구인 게시판 전체 글 조회 로직(카테고리 상관 X) */
    @Transactional
    public List<MentorPostResponse> findAllPosts() {
        List<MentorPost> mentorPosts = mentorPostRepository.findAll();

        return mentorPosts.stream()
                .map(this::convertToMentorPostResponse)
                .collect(Collectors.toList());
    }

    /* 구인 게시판 전체 글 최신 순 정렬 조회 로직 */
    @Transactional
    public List<MentorPostResponse> findAllPostsOrderByCreatedAtDesc() {
        List<MentorPost> mentorPostList = mentorPostRepository.findAllByOrderByCreatedAtDesc();

        return mentorPostList.stream()
                .map(this::convertToMentorPostResponse)
                .collect(Collectors.toList());
    }

    /* 구인 게시판 작성자를 통한 글 조회 로직 */
    @Transactional
    public List<MentorPostResponse> findPostsByWriter(String username) {
        List<MentorPost> mentorPosts = mentorPostRepository.findAllByUserUsername(username);

        return mentorPosts.stream()
                .map(this::convertToMentorPostResponse)
                .collect(Collectors.toList());
    }

    /* 구인 게시판 유저Id를 통한 작성 글 조회 로직 */
    @Transactional
    public List<MentorPostResponse> findPostsByUserId(Long userId) {
        List<MentorPost> mentorPostList = mentorPostRepository.findMentorPostsByUserId(userId);

        return mentorPostList.stream()
                .map(this::convertToMentorPostResponse)
                .collect(Collectors.toList());
    }

    /* 구인 게시판 유저 Id를 통한 작성 글 최신 조회 로직 */
    @Transactional
    public List<MentorPostResponse> findPostsByUserIdOrderByCreatedAtDesc(Long userId) {
        List<MentorPost> mentorPostList = mentorPostRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        return mentorPostList.stream()
                .map(this::convertToMentorPostResponse)
                .collect(Collectors.toList());
    }

    /* 게시글 고유 id를 통한 특정 글 조회 로직 */
    @Transactional
    public MentorPostResponse findPostById(Long id) {
        MentorPost mentorPost = mentorPostRepository.findById(id).orElseThrow();
        return this.convertToMentorPostResponse(mentorPost);
    }

    /* 게시글 제목을 통한 특정 글 조회 로직 */
    @Transactional
    public List<MentorPostResponse> findPostByTitle(String title) {
        return mentorPostRepository.findMentorPostsByTitle(title).stream()
                .map(this::convertToMentorPostResponse)
                .collect(Collectors.toList());
    }

    /* 카테고리를 통한 글 조회 로직 */
    @Transactional
    public List<MentorPostResponse> findPostsByCategory(Category category) {
        return mentorPostRepository.findMentorPostsByCategory(category).stream()
                .map(this::convertToMentorPostResponse)
                .collect(Collectors.toList());
    }

    /* 카테고리를 통한 글 최신 순 조회 로직 */
    @Transactional
    public List<MentorPostResponse> findPostsByCategoryOrderByDesc(Category category) {
        return mentorPostRepository.findAllByCategoryOrderByCreatedAtDesc(category).stream()
                .map(this::convertToMentorPostResponse)
                .collect(Collectors.toList());
    }

    /* 글 삭제 */
    @Transactional
    public void deletePost(Long id) {
        mentorPostRepository.deleteById(id);
    }

    private MentorPostResponse convertToMentorPostResponse(MentorPost mentorPost) {
        return MentorPostResponse.builder()
                .id(mentorPost.getId())
                .category(mentorPost.getCategory())
                .title(mentorPost.getTitle())
                .text(mentorPost.getText())
                .username(mentorPost.getUser().getUsername())
                .writer(mentorPost.getWriter())
                .major(mentorPost.getMajor())
                .likes(mentorPost.getLikes())
                .scrapCount(mentorPost.getScrapCount())
                .createdAt(mentorPost.getCreatedAt())
                .modifiedAt(mentorPost.getModifiedAt())
                .build();
    }
}
