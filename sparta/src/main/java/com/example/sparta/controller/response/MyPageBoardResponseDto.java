package com.example.sparta.controller.response;

import com.example.sparta.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class MyPageBoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String name;
    private List comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MyPageBoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getBoardTitle();
        this.content = board.getBoardContent();
        this.name = board.getMember().getName();
        this.comments = board.getComments();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
