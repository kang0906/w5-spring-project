package com.example.sparta.entity;

import com.example.sparta.controller.request.BoardRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board extends Timestamped {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne//(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String boardTitle;

    @Column(nullable = false)
    private String boardContent;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Board(Member member, String boardTitle, String boardContent) {
        this.member = member;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }



    public void update(BoardRequestDto requestDto) {
        this.boardTitle = requestDto.getTitle();
        this.boardContent = requestDto.getContents();
    }

}
