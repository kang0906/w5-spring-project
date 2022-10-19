package com.example.sparta.controller.response;

import com.example.sparta.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@ToString
public class MyPageBoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String name;
    private Map<String, String> comments = new LinkedHashMap<>();
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MyPageBoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getBoardTitle();
        this.content = board.getBoardContent();
        this.name = board.getMember().getName();
        this.comments = null;   // todo : 수정해야함
        this.createdAt = null;  // todo : 수정해야함
        this.modifiedAt = null; // todo : 수정해야함
    }
}
