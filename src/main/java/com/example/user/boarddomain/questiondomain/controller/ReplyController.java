package com.example.user.boarddomain.questiondomain.controller;

import com.example.user.boarddomain.questiondomain.dto.ReplyDTO;
import com.example.user.boarddomain.questiondomain.entity.Reply;
import com.example.user.boarddomain.questiondomain.service.ReplyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question_post")
@AllArgsConstructor
public class ReplyController {

    private final ReplyService replyService;


    /* 답변 등록 */
    @PostMapping("/{post_id}/reply")
    public ResponseEntity<Reply> saveReply(@PathVariable("post_id") Long post_id, @RequestBody ReplyDTO replyDTO) {

        Reply reply = replyService.saveReply(replyDTO, post_id);

        return ResponseEntity.ok(reply);
    }


    /* 특정 글 모든 답변 return */
    @GetMapping("/{post_id}/reply")
    public List<Reply> getReplies(@PathVariable Long post_id) {
        return replyService.getAllReplies(post_id);
    }
}
