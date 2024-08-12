package com.example.user.userdomain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class UserBaseTimeEntity {

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
        this.createdAt = truncateToSeconds(this.createdAt);
        this.modifiedAt = createdAt;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.modifiedAt = truncateToSeconds(this.modifiedAt);
    }

    private LocalDateTime truncateToSeconds(LocalDateTime dateTime) {
        return dateTime.withNano(0);
    }
}