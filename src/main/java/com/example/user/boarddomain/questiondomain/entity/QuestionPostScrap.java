package com.example.user.boarddomain.questiondomain.entity;

import com.example.user.userdomain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionPostScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_post_id")
    private QuestionPost questionPost;
}
