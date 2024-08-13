package com.example.user.boarddomain.mentordomain.repository;

import com.example.user.boarddomain.mentordomain.entity.Category;
import com.example.user.boarddomain.mentordomain.entity.MentorPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorPostRepository extends JpaRepository<MentorPost, Long> {

    List<MentorPost> findMentorPostsByWriter(String writer);

    List<MentorPost> findMentorPostsByTitle(String title);

    List<MentorPost> findMentorPostsByCategory(Category category);
}
