package com.example.user.boarddomain.questiondomain.entity;

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
public class QuestionPost extends QuestionPostBaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /* 수업 명 */
    @Column(nullable = false)
    private String subject;

    /* 본문 */
    @Lob
    @Column(nullable = false)
    private String text;

    /* 작성자 */
    @Column(nullable = false)
    private String writer;

    /* 좋아요 */
    @ColumnDefault("0")
    private int likes;

    /* 스크랩 수 */
    @ColumnDefault("0")
    private int clips;

    /* 답변 수 */
    @ColumnDefault("0")
    @Column(name = "reply_count")
    private int replyCount;

}
