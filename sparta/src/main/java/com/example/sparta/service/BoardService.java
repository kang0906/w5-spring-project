package com.example.sparta.service;


import com.example.sparta.controller.request.BoardRequestDto;
import com.example.sparta.controller.response.CommentResponseDto;
import com.example.sparta.controller.response.CommonResponseDto;
import com.example.sparta.controller.response.DetailPostDto;
import com.example.sparta.entity.Board;
import com.example.sparta.entity.Comment;
import com.example.sparta.entity.Member;
import com.example.sparta.repository.BoardRepository;
import com.example.sparta.repository.CommentRepository;
import com.example.sparta.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;


    private Member getMember(String email) {
        Optional<Member> mem = memberRepository.findByEmail(email);
        if(!mem.isPresent())
            throw new IllegalArgumentException("사용자 정보가 없습니다!");
        return mem.get();
    }

    //게시글 업데이트
    @Transactional
    public DetailPostDto update(Long id, BoardRequestDto requestDto, String email) {
        Member member = getMember(email);
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));
        if(!board.getMember().getEmail().equals(member.getEmail()))
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        board.update(requestDto);
        return new DetailPostDto(board);

    }

    //게시글 저장
    @Transactional
    public DetailPostDto create(BoardRequestDto requestDto, String email)  {
        Member member = getMember(email);
        Board board = Board.builder()
                .boardTitle(requestDto.getTitle())
                .boardContent(requestDto.getContents())
                .member(member)
                .build();
        boardRepository.save(board);
        return DetailPostDto.builder()
                .id(board.getId())
                .name(board.getMember().getEmail())
                .title(board.getBoardTitle())
                .content(board.getBoardContent())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .build();
    }

    //게시글 삭제
    @Transactional
    public void delete(Long id, String email)  {
        Member member = getMember(email);
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));
        if(!board.getMember().getEmail().equals(member.getEmail()))
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        boardRepository.deleteById(id);
        List<Comment> list = commentRepository.findAllByBoardId(id);
        for(Comment comment : list) {
            commentRepository.deleteById(comment.getId());
        }
    }

    //게시글 상세보기
    @Transactional
    public CommonResponseDto<?> getDetailPost(Long id) {
        Board board = isPresentBoard(id);
        if (null == board) {
            return CommonResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        List<Comment> commentList = commentRepository.findAllByBoardId(id);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .name(comment.getMember().getEmail())
                            .comment(comment.getComment_content())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }

        return CommonResponseDto.success(
                DetailPostDto.builder()
                        .id(board.getId())
                        .title(board.getBoardTitle())
                        .content(board.getBoardContent())
                        .commentResponseDtoList(commentResponseDtoList)
                        .name(board.getMember().getEmail())
                        .createdAt(board.getCreatedAt())
                        .modifiedAt(board.getModifiedAt())
                        .build()
        );
    }

    //글전체보기
    @Transactional(readOnly = true)
    public CommonResponseDto<?> getPosts() {
        return CommonResponseDto.success(boardRepository.findAllByOrderByModifiedAtDesc());
    }

    @Transactional
    public Board isPresentBoard (Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        return optionalBoard.orElse(null);
    }

}