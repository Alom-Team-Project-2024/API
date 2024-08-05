package com.example.arom.boarddomain.repository;

import com.example.arom.boarddomain.entity.QuestionReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionReplyRepository extends JpaRepository<QuestionReply, Long> {
}
