package com.example.user.boarddomain.tutordomain.entity;

import com.example.user.userdomain.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Slf4j
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class TutorPost extends TutorBaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /* 글 카테고리 (멘토 구해요 / 멘티 구해요) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    /* 구인 게시판 글 제목 */
    @Column(nullable = false)
    private String title;

    /* 구인 게시판 글 본문 */
    @Column(nullable = false)
    private String text;

    /* 글 작성자 */
    @Column(nullable = false)
    private String writer;

    /* 학과 */
    @Column(nullable = false)
    private String major;

    /* 과목명 */
    @Column(nullable = false)
    private String subject;

    /* 좋아요 */
    @Column(nullable = false)
    @ColumnDefault("0")
    private int likes;

}
