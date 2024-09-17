package com.example.user.boarddomain.questiondomain.repository;

import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionPostRepository extends JpaRepository<QuestionPost, Long> {
    List<QuestionPost> findAllByUserUsername(String username);

    List<QuestionPost> findAllBySubject(String subject);

    List<QuestionPost> findAllByUserId(Long id);

    List<QuestionPost> findAllByOrderByCreatedAtDesc();

    List<QuestionPost> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
