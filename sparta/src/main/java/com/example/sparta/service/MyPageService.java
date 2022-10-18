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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;

    public List<MyPageBoardResponseDto> getMyBoard(String memberName){
        Member member = memberRepository.findByName(memberName).orElseThrow();
        List<Board> allByMember_id = boardRepository.findAllByMember_id(member.getId());
//        List<MyPageBoardResponseDto> returnList = new ArrayList<>();
//        for (Board board : allByMember_id) {
//            returnList.add(new MyPageBoardResponseDto(board));
//        }
//        return returnList;

        return allByMember_id.stream()
                .map(m -> new MyPageBoardResponseDto(m))
                .collect(Collectors.toList());
    }

    public List<MyPageCommentResponseDto> getMyComment(String memberName){
        Member member = memberRepository.findByName(memberName).orElseThrow();
        List<Comment> allByMember_id = commentRepository.findAllByMember_id(member.getId());
//        List<MyPageCommentResponseDto> returnList = new ArrayList<>();
//        for (Comment comment : allByMember_id) {
//            returnList.add(new MyPageCommentResponseDto(comment));
//        }
//        return returnList;

        return allByMember_id.stream()
                .map(m -> new MyPageCommentResponseDto(m))
                .collect(Collectors.toList());
    }

    public List<MyPageLikesResponseDto> getMyLikes(String memberName){
        Member member = memberRepository.findByName(memberName).orElseThrow();
        List<Likes> allByMember_id = likesRepository.findAllByMember_id(member.getId());
//        List<MyPageLikesResponseDto> returnList = new ArrayList<>();
//        for (Likes likes : allByMember_id) {
//            returnList.add(new MyPageLikesResponseDto(likes));
//        }
//        return returnList;

        return allByMember_id.stream()
                .map(m -> new MyPageLikesResponseDto(m))
                .collect(Collectors.toList());
    }
}
