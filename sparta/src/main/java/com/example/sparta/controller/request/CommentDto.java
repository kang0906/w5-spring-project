package com.example.sparta.controller.request;

import com.example.sparta.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class CommentDto {
    private Member member;
    private String comment;
    private Long postId;
}
