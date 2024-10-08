package com.example.user.chatdomain.controller;

import com.example.user.chatdomain.dto.ChatRoomResponse;
import com.example.user.chatdomain.service.ChatRoomService;
import com.example.user.chatdomain.service.OneToOneChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@AllArgsConstructor
@Tag(name = "Chat", description = "채팅 API")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final OneToOneChatService oneToOneChatService;

    /* 전체 채팅방 목록 조회 */
    @Operation(summary = "전체 채팅방 목록 조회", description = "현재 DB에 저장되어있는 모든 채팅방 목록을 조회합니다.")
    @GetMapping("")
    private List<ChatRoomResponse> findAllRooms() {
        return chatRoomService.getAllChatRooms();
    }

    /* Id 값을 통한 특정 채팅방 조회 */
    @Operation(summary = "특정 채팅방 조회", description = "Id 값을 사용하여 특정 채팅방을 조회합니다.")
    @GetMapping("/id/{chat_id}")
    public ChatRoomResponse findRoomById(@PathVariable("chat_id") Long id) {
        return chatRoomService.getChatRoomById(id);
    }

    /* 채팅방 이름을 통한 채팅방 조회 */
    @Operation(summary = "채팅방 이름을 통한 조회", description = "채팅방 이름을 통해 특정 채팅방을 조회합니다. 주로 채팅방 검색에 사용합니다.")
    @GetMapping("/name/{name}")
    public ChatRoomResponse findRoomByName(@PathVariable("name") String name) {
        return chatRoomService.getChatRoomByName(name);
    }

    /* 새로운 채팅방 생성 */
    @Operation(summary = "새로운 채팅방 생성", description = "닉네임 2개를 받아 새로운 채팅방을 생성합니다.")
    @PostMapping("")
    public ResponseEntity<ChatRoomResponse> createChat(String nickname1, String nickname2) {
        ChatRoomResponse chatRoomResponse = oneToOneChatService.createChatRoomForUsers(nickname1, nickname2);
        return ResponseEntity.ok(chatRoomResponse);
    }

    /* 특정 유저가 참가중인 채팅방 목록 조회 */
    @Operation(summary = "특정 유저가 참가중인 채팅방 목록", description = "유저 학번을 파라미터로 받아 해당 유저가 참여중인 모든 채팅방의 목록을 조회합니다.")
    @GetMapping("/rooms/{username}")
    public List<ChatRoomResponse> findRoomsByUsername(@PathVariable("username") String username) {
        return chatRoomService.findRoomsByChatRoomName(username);
    }
}
