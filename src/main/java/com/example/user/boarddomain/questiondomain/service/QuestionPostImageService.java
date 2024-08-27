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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    @Transactional
    public List<QuestionPostImageDTO> saveImage(List<MultipartFile> files, Long id) throws IOException {
        QuestionPost questionPost = questionPostRepository.findById(id).orElseThrow();

        List<QuestionPostImageDTO> imageDTOS = new ArrayList<>();

        for (MultipartFile file : files) {
            // 파일 이름을 UUID로 대체
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String uuidFilename = UUID.randomUUID() + fileExtension;

            Path filePath = Paths.get(uploadDir, uuidFilename);

            // try-with-resources를 사용하여 파일 스트림을 자동으로 닫음
            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

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
    @Transactional
    public List<QuestionPostImageDTO> getAllImages(Long id) {
        QuestionPost questionPost = questionPostRepository.findById(id).orElseThrow();

        return questionPost.getImages().stream()
                .map(questionPostImage -> new QuestionPostImageDTO(questionPostImage.getImageUrl()))
                .collect(Collectors.toList());
    }
}