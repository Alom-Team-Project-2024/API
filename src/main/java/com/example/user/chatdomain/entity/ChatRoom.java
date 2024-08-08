package com.example.user.chatdomain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom extends ChatRoomBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /* 방 이름 */
    @Column(nullable = false)
    private String name;

    /* User와 다대다 매핑 -> UserChatRoom과 일대다 매칭 */
    @OneToMany(mappedBy = "chatRoom")
    private Set<UserChatRoom> userChatRooms = new HashSet<>();
}
