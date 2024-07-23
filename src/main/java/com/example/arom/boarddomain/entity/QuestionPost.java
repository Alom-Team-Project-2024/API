package com.example.arom.boarddomain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter @Setter
public class QuestionPost {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String subject;

    @Lob
    private String text;

    private String writer;

    @ColumnDefault("0")
    private int like;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private int field;

    private int field2;

    @OneToMany(mappedBy = "post")
    private List<PostImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<QuestionReply> replies = new ArrayList<>();

}
