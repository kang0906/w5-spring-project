package com.example.sparta.controller.response;

import com.example.sparta.entity.Comment;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class MyPageCommentResponseDto {
    private Long id;
    private String comment;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MyPageCommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment_content();
        this.username = comment.getMember().getName();

        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
