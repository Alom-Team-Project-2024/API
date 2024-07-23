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
public class QuestionReply {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private QuestionPost post;

    private String title;

    @Lob
    private String text;

    private String writer;

    @ColumnDefault("0")
    private int like;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "reply")
    private List<ReplyImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "reply")
    private List<ReplyComment> comments = new ArrayList<>();

}
