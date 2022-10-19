package com.example.sparta.controller;



import com.example.sparta.controller.response.CommonResponseDto;
import com.example.sparta.controller.response.DetailPostDto;
import com.example.sparta.security.UserDetailsImpl;
import com.example.sparta.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성
    @PostMapping("/api/post")
    public DetailPostDto createPost(@RequestBody com.sparta.mk.dto.BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetail)  {
        return boardService.create(requestDto, userDetail.getUsername());
    }

    // 게시글 수정
    @PutMapping("/api/post/{postId}")
    public DetailPostDto updatePost(@PathVariable Long id, @RequestBody com.sparta.mk.dto.BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetail)  {
        return boardService.update(id, requestDto, userDetail.getUsername());
    }

    // 게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public Long deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetail) {
        boardService.delete(id, userDetail.getUsername());
        return id;
    }

    // 게시글 전체 조회
    @GetMapping("/api/posts")
    public CommonResponseDto<?> getPosts() {
        return boardService.getPosts();
    }

    // 게시글 조회
    @GetMapping("/api/post/{postId}")
    public CommonResponseDto<?> getDetailPost(@PathVariable Long id) {
        return boardService.getDetailPost(id);
    }
}