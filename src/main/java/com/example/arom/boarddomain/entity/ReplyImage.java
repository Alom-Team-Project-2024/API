package com.example.arom.boarddomain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter @Setter
public class ReplyImage {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reply_id")
    private QuestionReply reply;

    private String url;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}