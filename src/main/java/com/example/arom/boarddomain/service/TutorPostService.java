package com.example.arom.boarddomain.service;

import com.example.arom.boarddomain.entity.CategoryStatus;
import com.example.arom.boarddomain.entity.TutorPost;
import com.example.arom.boarddomain.exception.ResourceNotFoundException;
import com.example.arom.boarddomain.repository.TutorPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutorPostService {
    private final TutorPostRepository tutorPostRepository;

    @Transactional
    public Long createPost(TutorPost post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        tutorPostRepository.save(post);
        return post.getId();
    }

    public List<TutorPost> getAllPosts() {
        return tutorPostRepository.findAll();
    }

    public TutorPost getPostById(Long id) {
        return tutorPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor post not found with id " + id));
    }

    @Transactional
    public void updatePost(Long id, String title, String text, String writer, String major, CategoryStatus category) {
        TutorPost existingPost = tutorPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor post not found with id " + id));

        existingPost.setTitle(title);
        existingPost.setText(text);
        existingPost.setWriter(writer);
        existingPost.setMajor(major);
        existingPost.setCategory(category);
        existingPost.setModifiedAt(LocalDateTime.now());
    }

    @Transactional
    public void deletePost(Long id) {
        if (!tutorPostRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tutor post not found with id " + id);
        }
        tutorPostRepository.deleteById(id);
    }
}
