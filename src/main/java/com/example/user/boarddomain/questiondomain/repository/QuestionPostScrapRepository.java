package com.example.user.boarddomain.questiondomain.repository;

import com.example.user.boarddomain.questiondomain.entity.QuestionPostScrap;
import com.example.user.userdomain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionPostScrapRepository extends JpaRepository<QuestionPostScrap, Long> {

    List<QuestionPostScrap> findAllByUser(User user);

    List<QuestionPostScrap> findAllByUserOrderByQuestionPostCreatedAtDesc(User user);
}
