package com.example.user.userdomain.entity;

import lombok.Getter;

@Getter
public enum Role {
    DELETE_ACCOUNT("삭제된 계정", -1),
    USER("일반 회원", 0),
    ADMIN("관리자", 1);

    private final String description;
    private final Integer level;

    Role(String description, Integer level) {
        this.description = description;
        this.level = level;
    }
}
