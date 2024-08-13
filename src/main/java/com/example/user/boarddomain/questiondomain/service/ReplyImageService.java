package com.example.user.boarddomain.questiondomain.service;

import com.example.user.boarddomain.questiondomain.dto.ReplyImageDTO;
import com.example.user.boarddomain.questiondomain.entity.QuestionPost;
import com.example.user.boarddomain.questiondomain.entity.QuestionPostImage;
import com.example.user.boarddomain.questiondomain.entity.Reply;
import com.example.user.boarddomain.questiondomain.entity.ReplyImage;
import com.example.user.boarddomain.questiondomain.repository.ReplyImageRepository;
import com.example.user.boarddomain.questiondomain.repository.ReplyRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
public class ReplyImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ReplyRepository replyRepository;
    private final ReplyImageRepository replyImageRepository;

    /* 답변 이미지 등록 로직 */
    public List<ReplyImageDTO> saveImage(List<MultipartFile> files, Long id) throws IOException {
        Reply reply = replyRepository.findById(id).orElseThrow();

        List<ReplyImageDTO> imageDTOS = new ArrayList<>();

        for (MultipartFile file : files) {
            Path copyLocation = Paths.get(uploadDir + "/" + file.getOriginalFilename());
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            ReplyImage replyImage = ReplyImage.builder()
                    .reply(reply)
                    .imageUrl(file.getOriginalFilename())
                    .build();

            replyImageRepository.save(replyImage);

            imageDTOS.add(new ReplyImageDTO(replyImage.getImageUrl()));
        }
        return imageDTOS;
    }

    /* 답변 이미지 조회 로직 */
    public List<ReplyImageDTO> getAllImages(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow();
        return reply.getImages().stream()
                .map(replyImage -> new ReplyImageDTO(replyImage.getImageUrl()))
                .collect(Collectors.toList());
    }
}
