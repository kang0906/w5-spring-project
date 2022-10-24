package com.example.sparta.controller.response;


import com.example.sparta.entity.Board;
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
// 글상세보기를 위한 Dto
public class DetailPostDto {
    private Long id;
    private String title;
    private String content;
    private String name;

    //private Long likeNum = commentRepository.countByPostId(board);

    private List<CommentResponseDto> commentResponseDtoList;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public DetailPostDto (Board board) {
        this.id = board.getId();
        this.title = board.getBoardTitle();
        this.name = board.getMember().getName();
        this.content = board.getBoardContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}

