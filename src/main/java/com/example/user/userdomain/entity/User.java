package com.example.user.userdomain.entity;

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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "nickname")
})
@EntityListeners(AuditingEntityListener.class)
public class User extends UserBaseTimeEntity {
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
    private String nickname;

    /* 프로필 사진 */
    @Column(name = "profile_image")
    private String profileImage;

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

    // 닉네임 변경
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    // 프로필 이미지 변경
    public void changeProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    // 온도 변경
    public void changePoint(double point) {
        this.point = point;
    }
}
