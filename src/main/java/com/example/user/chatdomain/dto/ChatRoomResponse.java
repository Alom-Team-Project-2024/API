package com.example.user.chatdomain.dto;

import com.example.user.userdomain.dto.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ChatRoomResponse {

    /* 채팅방 Id */
    @Schema(description = "채팅방 Id")
    private Long id;

    /* 채팅방 이름 */
    @Schema(description = "채팅방 이름")
    private String chatRoomName;

    /* 채팅방에 참여중인 유저 리스트 */
    @Schema(description = "채팅방에 참여중인 유저 리스트")
    private List<UserResponse> userResponseList;

    /* 채팅방 생성 시간 */
    @Schema(description = "채팅방 생성 시간")
    private LocalDateTime createdAt;

    /* 채팅방 수정 시간(마지막 메시지가 보내진 시간) */
    @Schema(description = "채팅방 수정 시간(마지막 메시지가 보내진 시간)")
    private LocalDateTime modifiedAt;
}
