package com.example.user.boarddomain.mentordomain.repository;

import com.example.user.boarddomain.mentordomain.entity.MentorPostScrap;
import com.example.user.userdomain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorPostScrapRepository extends JpaRepository<MentorPostScrap, Long> {
    List<MentorPostScrap> findAllByUser(User user);

    List<MentorPostScrap> findAllByUserOrderByMentorPostCreatedAtDesc(User user);
}
