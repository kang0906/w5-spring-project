package com.example.sparta.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Likes {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "board_id", nullable = false)
    @ManyToOne
    private Board board;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne//(fetch = FetchType.LAZY)
    private Member member;

    public Likes(Board board, Member member) {
        this.board = board;
        this.member = member;
    }
}
