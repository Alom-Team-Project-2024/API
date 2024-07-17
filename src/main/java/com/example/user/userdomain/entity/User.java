package com.example.user.userdomain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "nickname")
})
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /* 회원 아이디 */
    @Column(nullable = false)
    private String username;

    /* 회원 실명 */
    @Column(nullable = false)
    private String name;

    /* 닉네임 */
    @Column(nullable = false)
    private String nickname;

    /* 전공 */
    @Column(nullable = false)
    private String major;

    /* 학번 */
    @Column(name = "student_code", nullable = false)
    private int studentCode;

    /* 학년 */
    @Column(name = "student_grade", nullable = false)
    private int studentGrade;

    /* 학생 상태 */
    @Enumerated(EnumType.STRING)
    @Column(name = "registration_status", nullable = false)
    private RegistrationStatus registrationStatus;

    /* 역할 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /* 활동 점수 */
    @Column(nullable = false)
    @ColumnDefault("36.5")
    private double point;

    /* 가입 날짜 */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createAt;

    /* 수정 날짜 */
    @Column(name = "modified_at")
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
