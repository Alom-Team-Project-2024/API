package com.example.user.boarddomain.tutordomain.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Category {

    FIND_MENTOR("멘토 구해요"),
    FIND_MENTEE("멘티 구해요");

    private final String label;

    private String label() {
        return label;
    }
}
