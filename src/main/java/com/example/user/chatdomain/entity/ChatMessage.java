package com.example.user.chatdomain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Document
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage extends ChatMessageBaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String chatRoomId;

    private String userId;

    /* 메시지 내용 */
    private String message;
}
