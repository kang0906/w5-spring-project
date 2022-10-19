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

//    public DetailPostDto (Board board) {
//        this.id = board.getId();
//        this.title = board.getTitle();
//        this.name = board.getMember().getUsername();
//        this.content = board.getContents();
//        this.createAt = board.getCreateAt();
//        this.modifiedAt = board.getModifiedAt();
//    }
}

