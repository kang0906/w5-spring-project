package com.sparta.mk.dto;

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
