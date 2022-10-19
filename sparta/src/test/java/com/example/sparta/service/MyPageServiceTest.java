package com.example.sparta.service;

import com.example.sparta.controller.response.MyPageBoardResponseDto;
import com.example.sparta.controller.response.MyPageCommentResponseDto;
import com.example.sparta.controller.response.MyPageLikesResponseDto;
import com.example.sparta.entity.Board;
import com.example.sparta.entity.Comment;
import com.example.sparta.entity.Likes;
import com.example.sparta.entity.Member;
import com.example.sparta.repository.BoardRepository;
import com.example.sparta.repository.CommentRepository;
import com.example.sparta.repository.LikesRepository;
import com.example.sparta.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class MyPageServiceTest {

    @Autowired
    MyPageService myPageService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    LikesRepository likesRepository;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void before() {
        Member m = new Member(null, "kang", "kang@naver.com", "1234");
        Member kim = new Member(null, "kim", "kim@naver.com", "1234");
        memberRepository.save(m);
        memberRepository.save(kim);

        Board save1 = boardRepository.save(new Board( m, "title-1", "content-1"));
        Board save2 = boardRepository.save(new Board( m, "title-2", "content-2"));
        Board save3 = boardRepository.save(new Board( m, "title-3", "content-3"));

        Board save4 = boardRepository.save(new Board( kim, "title-4", "content-4"));
        Board save5 = boardRepository.save(new Board( kim, "title-5", "content-5"));

        commentRepository.save(new Comment(save1,m,"comment1"));
        commentRepository.save(new Comment(save2,m,"comment2"));
        commentRepository.save(new Comment(save3,m,"comment3"));

        commentRepository.save(new Comment(save4,kim,"comment4"));
        commentRepository.save(new Comment(save5,kim,"comment5"));

        commentRepository.save(new Comment(save4,m,"comment6(from kang to kim)"));

        likesRepository.save(new Likes(save1,m));
        likesRepository.save(new Likes(save2,m));
        likesRepository.save(new Likes(save3,m));

        likesRepository.save(new Likes(save4,kim));
        likesRepository.save(new Likes(save5,kim));
        likesRepository.save(new Likes(save5,m));
    }

    @AfterEach
    void after(){
        commentRepository.deleteAll();
        likesRepository.deleteAll();
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void getMyBoard() {
        System.out.println("start");
        List<MyPageBoardResponseDto> kang = myPageService.getMyBoard("kang");
        for (MyPageBoardResponseDto myPageBoardResponseDto : kang) {
            assertThat(myPageBoardResponseDto.getName()).isEqualTo("kang");
            System.out.println("myPageBoardResponseDto = " + myPageBoardResponseDto.toString());
        }
        System.out.println("end");

    }

    @Test
    void getMyComment() {
        System.out.println("start");
        List<MyPageCommentResponseDto> kang = myPageService.getMyComment("kang");
        for (MyPageCommentResponseDto myPageCommentResponseDto : kang) {
            assertThat(myPageCommentResponseDto.getUsername()).isEqualTo("kang");
            System.out.println("myPageCommentResponseDto = " + myPageCommentResponseDto);
        }
        System.out.println("end");
    }

    @Test
    void getMyLikes() {
        System.out.println("start");
        List<MyPageLikesResponseDto> kang = myPageService.getMyLikes("kang");
        for (MyPageLikesResponseDto myPageLikesResponseDto : kang) {
            assertThat(myPageLikesResponseDto.getUsername()).isEqualTo("kang");
            System.out.println("myPageLikesResponseDto = " + myPageLikesResponseDto);
        }
        System.out.println("end");
    }
}