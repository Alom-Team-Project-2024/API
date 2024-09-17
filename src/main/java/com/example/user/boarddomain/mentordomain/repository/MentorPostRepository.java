package com.example.user.boarddomain.mentordomain.repository;

import com.example.user.boarddomain.mentordomain.entity.Category;
import com.example.user.boarddomain.mentordomain.entity.MentorPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorPostRepository extends JpaRepository<MentorPost, Long> {

    List<MentorPost> findMentorPostsByTitle(String title);

    List<MentorPost> findMentorPostsByCategory(Category category);

    List<MentorPost> findMentorPostsByUserId(Long userId);

    List<MentorPost> findAllByOrderByCreatedAtDesc();

    List<MentorPost> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    List<MentorPost> findAllByCategoryOrderByCreatedAtDesc(Category category);

    List<MentorPost> findAllByUserUsername(String username);
}
