package com.example.user.boarddomain.questiondomain.repository;

import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionPostRepository extends JpaRepository<QuestionPost, Long> {
    List<QuestionPost> findAllByWriter(String username);

    List<QuestionPost> findAllByUserId(Long id);
}
