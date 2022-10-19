package com.example.sparta.controller;




import com.example.sparta.controller.response.CommentResponseDto;
import com.example.sparta.controller.response.CommonResponseDto;
import com.example.sparta.security.UserDetailsImpl;
import com.example.sparta.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    //댓글 쓰기
    @PostMapping("/api/post/{postId}/comment")
    public CommentResponseDto createComment(@RequestBody com.sparta.mk.dto.CommentDto commentDto, @AuthenticationPrincipal UserDetailsImpl userDetail) throws IllegalAccessException {
        return commentService.createComment(commentDto, userDetail.getUsername());
    }

    //댓글 수정
    @PutMapping("/api/post/{postId}/comment/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody com.sparta.mk.dto.CommentDto commentDto, @AuthenticationPrincipal UserDetailsImpl userDetail) throws IllegalAccessException {
        return commentService.updateComment(id, commentDto, userDetail.getUsername());
    }

    //댓글 삭제
    @DeleteMapping("/api/post/{postId}/comment/{commentId}")
    public Long deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetail) throws IllegalAccessException {
        return commentService.deleteComment(id, userDetail.getUsername());
    }


    //댓글 목록 조회
    @GetMapping("/api/comment/{id}")
    public CommonResponseDto<?> getComment(@PathVariable Long id) {
        return commentService.getCommentList(id);
    }
}
