package com.example.user.boarddomain.questiondomain.service;

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
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ReplyRepository replyRepository;

    /* 답변 이미지 등록 로직 */
    public ReplyImage saveImage(MultipartFile file, Long id) throws IOException {
        Reply reply = replyRepository.findById(id).orElseThrow();

        Path copyLocation = Paths.get(uploadDir + "/" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        ReplyImage replyImage = ReplyImage.builder()
                .reply(reply)
                .imageUrl(file.getOriginalFilename())
                .build();

        return replyImage;
    }

    /* 답변 이미지 조회 로직 */
    public List<ReplyImage> getAllImages(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow();
        return reply.getImages();
    }
}
