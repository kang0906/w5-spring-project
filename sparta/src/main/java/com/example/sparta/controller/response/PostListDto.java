package com.example.sparta.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
// 글목록가져오기위한 Dto
public class PostListDto {
    private Long id;
    private String title;
    private String content;
    private String name;
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
