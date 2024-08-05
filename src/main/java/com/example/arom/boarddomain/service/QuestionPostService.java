package com.example.arom.boarddomain.service;

import com.example.arom.boarddomain.entity.PostImage;
import com.example.arom.boarddomain.entity.QuestionPost;
import com.example.arom.boarddomain.entity.QuestionReply;
import com.example.arom.boarddomain.exception.ResourceNotFoundException;
import com.example.arom.boarddomain.repository.PostImageRepository;
import com.example.arom.boarddomain.repository.QuestionPostRepository;
import com.example.arom.boarddomain.repository.QuestionReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionPostService {

    private final QuestionPostRepository questionPostRepository;
    private final PostImageRepository postImageRepository;
    private final QuestionReplyRepository questionReplyRepository;

    @Transactional
    public Long createPost(QuestionPost post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        for (PostImage image : post.getImages()) {
            image.setPost(post);
            postImageRepository.save(image);
        }

        for (QuestionReply reply : post.getReplies()) {
            reply.setPost(post);
            questionReplyRepository.save(reply);
        }
        questionPostRepository.save(post);
        return post.getId();
    }
    public List<QuestionPost> getAllPost(){
        return questionPostRepository.findAll();
    }
    public QuestionPost getPostById(Long id) {
        return questionPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }
    @Transactional
    public void updatePost(Long id, QuestionPost updatedPost) {
        QuestionPost existingPost = questionPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));

        existingPost.setSubject(updatedPost.getSubject());
        existingPost.setText(updatedPost.getText());
        existingPost.setWriter(updatedPost.getWriter());
        existingPost.setField(updatedPost.getField());
        existingPost.setField2(updatedPost.getField2());
        existingPost.setImages(updatedPost.getImages());
        existingPost.setReplies(updatedPost.getReplies());
        existingPost.setModifiedAt(LocalDateTime.now());
    }
    @Transactional
    public void deletePost(Long id) {
        if (!questionPostRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id " + id);
        }
        questionPostRepository.deleteById(id);
    }
}
