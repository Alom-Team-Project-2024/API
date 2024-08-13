package com.example.user.chatdomain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class ChatRoomBaseTimeEntity {

    /* 생성 날짜 */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* 수정 날짜 */
    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @PrePersist
    public void onPrePersist() {
        // createdAt이 null인 경우 현재 시간으로 설정
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.createdAt = truncateToSeconds(this.createdAt);

        // modifiedAt을 createdAt과 동일하게 설정
        this.modifiedAt = this.createdAt;
    }

    @PreUpdate
    public void onPreUpdate() {
        // modifiedAt이 null인 경우 현재 시간으로 설정
        if (this.modifiedAt == null) {
            this.modifiedAt = LocalDateTime.now();
        }
        this.modifiedAt = truncateToSeconds(this.modifiedAt);
    }

    private LocalDateTime truncateToSeconds(LocalDateTime dateTime) {
        return dateTime.withNano(0);
    }
}
