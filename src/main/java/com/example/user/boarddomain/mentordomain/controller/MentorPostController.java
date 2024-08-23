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
    public MentorPostResponse post(@ModelAttribute MentorPostDTO mentorPostDTO) {
        return mentorPostService.savePost(mentorPostDTO);
    }

    /* 구인 게시판에 등록된 전체 글 조회 */
    @Operation(summary = "구인 게시판 전체 글 조회", description = "구인 게시판에 등록된 전체 글을 조회합니다.(카테고리 상관 없이 모든 글 조회)")
    @GetMapping("")
    public List<MentorPostResponse> getAllPosts() {
        return mentorPostService.findAllPosts();
    }

    /* 특정 사용자가 구인 게시판에 작성한 모든 글 조회 */
    @Operation(summary = "작성자를 통한 구인 게시판 글 조회", description = "특정 유저가 작성한 글을 조회합니다.(카테고리 상관 없이 조회)")
    @Parameter(name = "username", description = "작성자 검색을 위해 String 형식의 username 입력")
    @GetMapping("/username/{username}")
    public List<MentorPostResponse> getPostsByWriter(@PathVariable("username") String username) {
        return mentorPostService.findPostsByWriter(username);
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
    @Parameter(name = "category", description = "카테고리 선택")
    @GetMapping("/category/{category}")
    public List<MentorPostResponse> getPostsByCategory(@PathVariable("category") Category category) {
        return mentorPostService.findPostsByCategory(category);
    }

    /* 글 삭제 */
    @Operation(summary = "글 삭제", description = "멘토 멘티 매칭 후 해당 글을 삭제합니다.")
    @Parameter(name = "id", description = "삭제하려는 글의 id 값 입력")
    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") Long id) {
        mentorPostService.deletePost(id);
    }
}
