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
public class Comment extends Timestamped{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "board_id", nullable = false)
    @ManyToOne
    private Board board;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne//(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String comment_content;

    public Comment(Board board, Member member, String comment_content) {
        this.board = board;
        this.member = member;
        this.comment_content = comment_content;
    }
}
