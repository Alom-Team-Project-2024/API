package com.example.arom.boarddomain.entity;

import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class TutorPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tutorpost_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;

    @Lob
    private String text;

    private String writer;

    private String major;

    @ColumnDefault("0")
    private int like;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}
