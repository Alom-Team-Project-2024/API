package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.entity.QuestionPostImage;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostImageRepository;
import com.example.user.boarddomain.questiondomain.repository.QuestionPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionPostImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final QuestionPostImageRepository questionPostImageRepository;
    private final QuestionPostRepository questionPostRepository;

    /* 질문 게시판 이미지 저장 로직 */
    public QuestionPostImage saveImage(MultipartFile file, Long id) throws IOException {
        QuestionPost questionPost = questionPostRepository.findById(id).orElseThrow();

        Path copyLocation = Paths.get(uploadDir + "/" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        QuestionPostImage questionPostImage = QuestionPostImage.builder()
                .questionPost(questionPost)
                .imageUrl(file.getOriginalFilename())
                .build();

        return questionPostImageRepository.save(questionPostImage);
    }

    /* 질문 게시글에 등록된 이미지 조회 */
    public List<QuestionPostImage> getAllImages(Long id) {
        QuestionPost questionPost = questionPostRepository.findById(id).orElseThrow();

        return questionPost.getImages();
    }
}