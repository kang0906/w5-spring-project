package com.example.sparta.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {

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

    public Board(Member member, String boardTitle, String boardContent) {
        this.member = member;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }
}
