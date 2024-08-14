package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.QuestionPostImageDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionPostImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final QuestionPostImageRepository questionPostImageRepository;
    private final QuestionPostRepository questionPostRepository;

    /* 질문 게시판 이미지 저장 로직 */
    public List<QuestionPostImageDTO> saveImage(List<MultipartFile> files, Long id) throws IOException {
        QuestionPost questionPost = questionPostRepository.findById(id).orElseThrow();

        List<QuestionPostImageDTO> imageDTOS = new ArrayList<>();

        // 업로드 디렉토리 확인 및 생성
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for(MultipartFile file : files) {
            Path copyLocation = Paths.get(uploadDir + "/" + file.getOriginalFilename());
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            QuestionPostImage questionPostImage = QuestionPostImage.builder()
                    .questionPost(questionPost)
                    .imageUrl(file.getOriginalFilename())
                    .build();
            questionPostImageRepository.save(questionPostImage);
            imageDTOS.add(new QuestionPostImageDTO(questionPostImage.getImageUrl()));
        }

        return imageDTOS;
    }

    /* 질문 게시글에 등록된 이미지 조회 */
    public List<QuestionPostImageDTO> getAllImages(Long id) {
        QuestionPost questionPost = questionPostRepository.findById(id).orElseThrow();

        return questionPost.getImages().stream()
                        .map(questionPostImage -> new QuestionPostImageDTO(questionPostImage.getImageUrl()))
                                .collect(Collectors.toList());
    }
}