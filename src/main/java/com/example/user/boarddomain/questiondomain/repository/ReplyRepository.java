package com.example.user.boarddomain.questiondomain.repository;

import com.example.user.boarddomain.questiondomain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
