package com.example.user.boarddomain.mentordomain.service;

import com.example.user.boarddomain.mentordomain.dto.MentorPostDTO;
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

@Service
@Slf4j
@AllArgsConstructor
public class MentorPostService {

    private final MentorPostRepository mentorPostRepository;
    private final UserRepository userRepository;

    /* 구인 게시판 글 등록 로직 */
    @Transactional
    public MentorPost savePost(MentorPostDTO tutorPostDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        MentorPost tutorPost = MentorPost.builder()
                .user(user)
                .category(tutorPostDTO.getCategory())
                .title(tutorPostDTO.getTitle())
                .text(tutorPostDTO.getText())
                .writer(username)
                .major(user.getMajor())
                .build();

        return mentorPostRepository.save(tutorPost);
    }

    /* 구인 게시판 전체 글 조회 로직(카테고리 상관 X) */
    @Transactional
    public List<MentorPost> findAllPosts() {
        return mentorPostRepository.findAll();
    }

    /* 구인 게시판 작성자를 통한 글 조회 로직 */
    @Transactional
    public List<MentorPost> findPostsByWriter(String username) {
        return mentorPostRepository.findMentorPostsByWriter(username);
    }

    /* 게시글 고유 id를 통한 특정 글 조회 로직 */
    @Transactional
    public MentorPost findPostById(Long id) {
        return mentorPostRepository.findById(id).orElseThrow();
    }

    /* 게시글 제목을 통한 특정 글 조회 로직 */
    @Transactional
    public List<MentorPost> findPostByTitle(String title) {
        return mentorPostRepository.findMentorPostsByTitle(title);
    }

    /* 카테고리를 통한 글 조회 로직 */
    @Transactional
    public List<MentorPost> findPostsByCategory(Category category) {
        return mentorPostRepository.findMentorPostsByCategory(category);
    }

    /* 글 삭제 */
    @Transactional
    public void deletePost(Long id) {
        mentorPostRepository.deleteById(id);
    }
}
