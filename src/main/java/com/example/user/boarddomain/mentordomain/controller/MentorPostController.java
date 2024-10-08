package com.example.user.boarddomain.mentordomain.controller;

import com.example.user.boarddomain.mentordomain.dto.MentorPostDTO;
import com.example.user.boarddomain.mentordomain.dto.MentorPostResponse;
import com.example.user.boarddomain.mentordomain.entity.Category;
import com.example.user.boarddomain.mentordomain.service.MentorPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentor_post")
@AllArgsConstructor
@Tag(name = "Mentor-Post", description = "구인게시판 API")
public class MentorPostController {

    private final MentorPostService mentorPostService;

    /* 구인 게시판에 글 등록 */
    @Operation(summary = "구인 게시판 글 등록", description = "구인 게시판에 글을 등록합니다.")
    @PostMapping("")
    public MentorPostResponse post(@RequestBody MentorPostDTO mentorPostDTO) {
        return mentorPostService.savePost(mentorPostDTO);
    }

    /* 구인 게시판에 등록된 전체 글 조회 */
    @Operation(summary = "구인 게시판 전체 글 조회", description = "구인 게시판에 등록된 전체 글을 조회합니다.(카테고리 상관 없이 모든 글 조회)")
    @GetMapping("")
    public List<MentorPostResponse> getAllPosts() {
        return mentorPostService.findAllPosts();
    }

    /* 구인 게시판에 등록된 젠체 글 최신순 조회 */
    @Operation(summary = "구인 게시판에 작성된 전체 글 최신 순 조회", description = "구인 게시판에 작성된 전체 글을 최신순으로 정렬 후 조회합니다.")
    @GetMapping("/desc")
    public List<MentorPostResponse> getAllPostsOrderByCreatedAtDesc() {
        return mentorPostService.findAllPostsOrderByCreatedAtDesc();
    }

    /* 특정 사용자가 구인 게시판에 작성한 모든 글 조회 */
    @Operation(summary = "작성자를 통한 구인 게시판 글 조회", description = "유저 학번을 파라미터로 받아 해당 유저가 작성한 모든 구인 글을 조회합니다.(카테고리 상관 없이 조회)")
    @Parameter(name = "username", description = "작성자 검색을 위해 String 형식의 학번 입력")
    @GetMapping("/username/{username}")
    public List<MentorPostResponse> getPostsByWriter(@PathVariable("username") String username) {
        return mentorPostService.findPostsByWriter(username);
    }

    /* 유저 Id를 통해 해당 사용자가 구인 게시판에 작성한 모든 글 조회 */
    @Operation(summary = "유저 Id를 통한 구인 게시판 글 조회", description = "유저 Id를 파라미터로 받아 해당 유저가 작성한 모든 구인 글을 조회합니다. (카테고리 상관 없이 조회")
    @Parameter(name = "userId", description = "작성자 검색을 위해 Long 형식의 userId 입력")
    @GetMapping("/userId/{userId}")
    public List<MentorPostResponse> getPostsByUserId(@PathVariable("userId") Long userId) {
        return mentorPostService.findPostsByUserId(userId);
    }

    /* 유저 Id를 통해 해당 사용자가 구인 게시판에 작성한 모든 글 최신순 조회 */
    @Operation(summary = "유저 Id를 통한 구인 게시판 글 최신순 조회", description = "유저 Id를 파라미터로 받아 해당 유저가 적성한 모든 구인 글을 최신순으로 정렬 후 조회합니다. (카테고리 상관 없이 조회")
    @Parameter(name = "userId", description = "작성자 검색을 위해 Long 형식의 userId 입력")
    @GetMapping("/userId/{userId}/desc")
    public List<MentorPostResponse> getPostsByUserIdOrderByCreatedAtDesc(@PathVariable("userId") Long userId) {
        return mentorPostService.findPostsByUserIdOrderByCreatedAtDesc(userId);
    }

    /* 특정 글 조회(글 고유 id 조회) */
    @Operation(summary = "게시글 고유 id를 통한 글 조회", description = "게시글 id를 통해 특정 글을 조회합니다.")
    @Parameter(name = "id", description = "게시글 id 입력")
    @GetMapping("/{id}")
    public MentorPostResponse getPostById(@PathVariable("id") Long id) {
        return mentorPostService.findPostById(id);
    }

    /* 글 제목을 통한 글 조회 */
    @Operation(summary = "글 제목으르 통한 글 조회", description = "게시글 제목을 통해 특정 글을 조회합니다.")
    @Parameter(name = "title", description = "검색하고 싶은 글의 제목 입력")
    @GetMapping("/title/{title}")
    public List<MentorPostResponse> getPostsByTitle(@PathVariable("title") String title) {
        return mentorPostService.findPostByTitle(title);
    }

    /* 카테고리를 통한 전체 글 조회 */
    @Operation(summary = "카테고리 글 조회", description = "카테고리에 해당하는 전체 글을 조회합니다.")
    @Parameter(name = "category", description = "카테고리")
    @GetMapping("/category/{category}")
    public List<MentorPostResponse> getPostsByCategory(@PathVariable("category") Category category) {
        return mentorPostService.findPostsByCategory(category);
    }

    /* 카테고리를 통한 전체 글 최신 순 조회 */
    @Operation(summary = "카테고리 글 최신 순 조회", description = "카테고리에 해당하는 구인 게시판 글을 최신순으로 정렬하여 조회합니다.")
    @Parameter(name = "category", description = "카테고리")
    @GetMapping("/category/{category}/desc")
    public List<MentorPostResponse> getPostsByCategoryOrderByDesc(@PathVariable("category") Category category) {
        return mentorPostService.findPostsByCategoryOrderByDesc(category);
    }

    /* 글 삭제 */
    @Operation(summary = "글 삭제", description = "멘토 멘티 매칭 후 해당 글을 삭제합니다.")
    @Parameter(name = "id", description = "삭제하려는 글의 id 값 입력")
    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") Long id) {
        mentorPostService.deletePost(id);
    }
}
