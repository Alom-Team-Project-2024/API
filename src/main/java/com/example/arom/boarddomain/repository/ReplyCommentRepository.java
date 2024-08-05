package com.example.arom.boarddomain.repository;

import com.example.arom.boarddomain.entity.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment,Long> {
}
