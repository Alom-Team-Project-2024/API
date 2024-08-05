package com.example.user.boarddomain.tutordomain.repository;

import com.example.user.boarddomain.tutordomain.entity.TutorPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorPostRepository extends JpaRepository<TutorPost, Long> {
}
