package com.example.user.boarddomain.questiondomain.controller;

import com.example.user.boarddomain.questiondomain.dto.ReplyDTO;
import com.example.user.boarddomain.questiondomain.entity.Reply;
import com.example.user.boarddomain.questiondomain.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question_post")
@AllArgsConstructor
@Tag(name = "reply-controller", description = "질문 게시판 답변 API")
public class ReplyController {

    private final ReplyService replyService;


    /* 답변 등록 */
    @Operation(summary = "답변 등록", description = "질문 게시글에 답변을 등록합니다.")
    @Parameter(name = "post_id", description = "질문 게시글 Id")
    @PostMapping("/{post_id}/reply")
    public ResponseEntity<Reply> saveReply(@PathVariable("post_id") Long post_id, @RequestBody ReplyDTO replyDTO) {

        Reply reply = replyService.saveReply(replyDTO, post_id);

        return ResponseEntity.ok(reply);
    }


    /* 특정 글 모든 답변 return */
    @Operation(summary = "답변 조회", description = "특정 질문글에 작성된 모든 답변을 조회합니다.")
    @Parameter(name = "post_id", description = "질문 게시글 Id")
    @GetMapping("/{post_id}/reply")
    public List<Reply> getReplies(@PathVariable Long post_id) {
        return replyService.getAllReplies(post_id);
    }
}
