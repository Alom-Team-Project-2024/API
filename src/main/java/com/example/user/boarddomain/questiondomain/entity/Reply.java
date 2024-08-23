package com.example.user.boarddomain.questiondomain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Slf4j
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Reply extends ReplyBaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private QuestionPost questionPost;

    /* 답변 본문 */
    @Lob
    @Column(nullable = false)
    private String text;

    /* 답변 작성자 */
    private String writer;

    /* 답변 좋아요 */
    @ColumnDefault("0")
    private int likes;

    /* 답변 이미지 */
    @OneToMany(mappedBy = "reply")
    private List<ReplyImage> images;

    /* 답변 좋아요 증가 */
    public void increaseLikes() {
        this.likes++;
    }

    /* 답변 좋아요 감소 */
    public void decreaseLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }
}
