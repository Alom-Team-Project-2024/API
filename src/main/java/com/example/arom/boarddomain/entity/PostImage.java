package com.example.arom.boarddomain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter @Setter
public class PostImage {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "postimage_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "images")
    private QuestionPost post;

    private String url;
    private LocalDateTime createdAt;

}