package com.example.arom.boarddomain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter @Setter
public class ReplyComment {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "reply_comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reply_id")
    private QuestionReply reply;

    private String content;

    private String writer;

    @ColumnDefault("0")
    private int like;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
