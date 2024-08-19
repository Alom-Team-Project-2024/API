package com.example.user.boarddomain.questiondomain.controller;

import com.example.user.boarddomain.questiondomain.dto.ReplyDTO;
import com.example.user.boarddomain.questiondomain.dto.ReplyResponse;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.entity.Reply;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostRepository;
import com.example.user.boarddomain.questiondomain.repository.ReplyRepository;
import com.example.user.boarddomain.questiondomain.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/question_post")
@AllArgsConstructor
@Tag(name = "Reply", description = "질문게시판 답변 API")
public class ReplyController {

    private final ReplyService replyService;

    /* 답변 등록 */
    @Operation(summary = "답변 등록", description = "제목 (title), 본문 (text)를 작성하여 질문 게시글에 답변을 등록합니다. 답변 등록 후 해당 답변의 id 값을 반환합니다.")
    @Parameter(name = "post_id", description = "질문 게시글 Id")
    @PostMapping("/{post_id}/reply")
    public ResponseEntity<Long> saveReply(@PathVariable("post_id") Long post_id, @RequestBody ReplyDTO replyDTO) {

        Reply reply = replyService.saveReply(replyDTO, post_id);

        return ResponseEntity.ok(reply.getId());
    }


    /* 특정 글 모든 답변 조회 */
    @Operation(summary = "답변 조회", description = "특정 질문글에 작성된 모든 답변을 조회합니다.")
    @Parameter(name = "post_id", description = "질문 게시글 Id")
    @GetMapping("/{post_id}/reply")
    public List<ReplyResponse> getReplies(@PathVariable Long post_id) {
        return replyService.getAllReplies(post_id);
    }

    /* 답변 좋아요 증가 */
    @Operation(summary = "답변 좋아요 증가", description = "답변의 Id 값을 전달하면 해당 답변의 좋아요 수가 1 증가합니다.")
    @Parameter(name = "reply_id", description = "답변 id")
    @PostMapping("/reply/{reply_id}/likes/up")
    public ResponseEntity<Integer> increaseLikes(@PathVariable("reply_id") Long id) {
        return ResponseEntity.ok(replyService.increaseLikes(id));
    }

    /* 답변 좋아요 감소 */
    @Operation(summary = "답변 좋아요 감소", description = "답변의 Id 값을 전달하면 해당 답변의 좋아요 수가 1 감소합니다. 단, 좋아요 수가 0인 답변의 경우 변동이 없습니다.")
    @Parameter(name = "reply_id", description = "해당 답변 id")
    @PostMapping("/reply/{reply_id}/likes/down")
    public ResponseEntity<Integer> decreaseLikes(@PathVariable("reply_id") Long id) {
        return ResponseEntity.ok(replyService.decreaseLikes(id));
    }
}
