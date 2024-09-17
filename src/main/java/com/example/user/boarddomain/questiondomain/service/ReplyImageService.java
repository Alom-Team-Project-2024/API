package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.ReplyImageDTO;
import com.example.user.boarddomain.questiondomain.entity.Reply;
import com.example.user.boarddomain.questiondomain.entity.ReplyImage;
import com.example.user.boarddomain.questiondomain.repository.ReplyImageRepository;
import com.example.user.boarddomain.questiondomain.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
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
public class ReplyImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ReplyRepository replyRepository;
    private final ReplyImageRepository replyImageRepository;

    /* 답변 이미지 등록 로직 */
    @Transactional
    public List<ReplyImageDTO> saveImage(List<MultipartFile> files, Long id) throws IOException {
        Reply reply = replyRepository.findById(id).orElseThrow();

        List<ReplyImageDTO> imageDTOS = new ArrayList<>();

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

            ReplyImage replyImage = ReplyImage.builder()
                    .reply(reply)
                    .imageUrl(uuidFilename)
                    .build();

            replyImageRepository.save(replyImage);
            imageDTOS.add(new ReplyImageDTO(replyImage.getImageUrl()));
        }
        return imageDTOS;
    }

    /* 답변 이미지 조회 로직 */
    @Transactional
    public List<ReplyImageDTO> getAllImages(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow();
        return reply.getImages().stream()
                .map(replyImage -> new ReplyImageDTO(replyImage.getImageUrl()))
                .collect(Collectors.toList());
    }
}
