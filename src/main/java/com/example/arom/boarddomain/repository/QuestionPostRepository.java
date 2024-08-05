package com.example.arom.boarddomain.repository;

import com.example.arom.boarddomain.entity.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionPostRepository extends JpaRepository<QuestionPost,Long> {
}
