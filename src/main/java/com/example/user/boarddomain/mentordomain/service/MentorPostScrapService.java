package com.example.user.boarddomain.mentordomain.service;

import com.example.user.boarddomain.mentordomain.dto.MentorPostDTO;
import com.example.user.boarddomain.mentordomain.entity.MentorPost;
import com.example.user.boarddomain.mentordomain.entity.MentorPostScrap;
import com.example.user.boarddomain.mentordomain.repository.MentorPostRepository;
import com.example.user.boarddomain.mentordomain.repository.MentorPostScrapRepository;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MentorPostScrapService {

    private final UserRepository userRepository;
    private final MentorPostRepository mentorPostRepository;
    private final MentorPostScrapRepository mentorPostScrapRepository;

    /* 구인 게시글 스크랩 등록 로직 */
    @Transactional
    public Integer postScrap(String username, Long post_id) {
        User user = userRepository.findByUsername(username);
        MentorPost mentorPost = mentorPostRepository.findById(post_id).orElseThrow();

        mentorPostScrapRepository.save(MentorPostScrap.builder()
                .user(user)
                .mentorPost(mentorPost)
                .build());

        mentorPost.increaseScrapCount();

        return mentorPost.getScrapCount();
    }

    /* 내가 스크랩 한 구인 글 조회 로직 */
    @Transactional
    public List<MentorPostDTO> getMyScrapList(String username) {
        User user = userRepository.findByUsername(username);
        List<MentorPost> mentorPostList = mentorPostScrapRepository.findAllByUser(user);

        return mentorPostList.stream()
                .map(mentorPost -> new MentorPostDTO(mentorPost.getTitle(), mentorPost.getText(), mentorPost.getCategory()))
                .collect(Collectors.toList());
    }
}
