package com.example.sparta.controller.response;

import com.example.sparta.entity.Likes;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MyPageLikesResponseDto {
    private Long id;
    private String content;
    private String username;

    public MyPageLikesResponseDto(Likes likes) {
        this.id = likes.getId();
        this.content = likes.getBoard().getBoardContent();
        this.username = likes.getMember().getName();
    }
}
