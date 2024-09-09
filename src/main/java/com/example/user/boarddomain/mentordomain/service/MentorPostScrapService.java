package com.example.user.boarddomain.mentordomain.service;

import com.example.user.boarddomain.mentordomain.dto.MentorPostResponse;
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
    public List<MentorPostResponse> getMyScrapList(String username) {
        List<MentorPostScrap> mentorPostScrapList = mentorPostScrapRepository.findAllByUserUsername(username);

        List<MentorPost> mentorPostList = mentorPostScrapList.stream()
                .map(MentorPostScrap::getMentorPost)
                .toList();

        return mentorPostList.stream()
                .map(this::convertToMentorPostResponse)
                .collect(Collectors.toList());
    }

    /* 내가 스크랩 한 구인 글 최신순 조회 로직 */
    @Transactional
    public List<MentorPostResponse> getMyScrapListOrderByDesc(String username) {
        User user = userRepository.findByUsername(username);
        List<MentorPostScrap> mentorPostScrapList = mentorPostScrapRepository.findAllByUserOrderByMentorPostCreatedAtDesc(user);

        List<MentorPost> mentorPostList = mentorPostScrapList.stream()
                .map(MentorPostScrap::getMentorPost)
                .toList();

        return mentorPostList.stream()
                .map(this::convertToMentorPostResponse)
                .collect(Collectors.toList());
    }

    private MentorPostResponse convertToMentorPostResponse(MentorPost mentorPost) {
        return MentorPostResponse.builder()
                .id(mentorPost.getId())
                .category(mentorPost.getCategory())
                .title(mentorPost.getTitle())
                .text(mentorPost.getText())
                .username(mentorPost.getUser().getUsername())
                .writer(mentorPost.getWriter())
                .major(mentorPost.getMajor())
                .likes(mentorPost.getLikes())
                .createdAt(mentorPost.getCreatedAt())
                .modifiedAt(mentorPost.getModifiedAt())
                .build();
    }
}
