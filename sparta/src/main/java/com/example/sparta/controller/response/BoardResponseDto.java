package com.example.sparta.controller.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String name;
    private List<CommentResponseDto> commentResponseDtoList;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
