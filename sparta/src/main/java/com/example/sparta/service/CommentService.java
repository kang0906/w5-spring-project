package com.example.sparta.service;



import com.example.sparta.controller.response.CommentResponseDto;
import com.example.sparta.controller.response.CommonResponseDto;
import com.example.sparta.entity.Board;
import com.example.sparta.entity.Comment;
import com.example.sparta.entity.Member;
import com.example.sparta.repository.BoardRepository;
import com.example.sparta.repository.CommentRepository;
import com.example.sparta.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository postRepository;

    private final BoardService boardService;

    private Member getMember(String username) {
        Optional<Member> mem = memberRepository.findByEmail(username);
        if(!mem.isPresent())
            throw new IllegalArgumentException("사용자 정보가 없습니다!");
        return mem.get();
    }

    private void extracted(com.sparta.mk.dto.CommentDto commentDto) {
        postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));
    }

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(com.sparta.mk.dto.CommentDto commentDto, String email)  {
        Member member = getMember(email);
        extracted(commentDto);
        Comment comment = new Comment(member);

        commentRepository.save(comment);
        return CommentResponseDto.builder()
                .id(comment.getId())
                .name(comment.getMember().getEmail())
                .comment(comment.getComment_content())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, com.sparta.mk.dto.CommentDto commentDto, String email) {
        Member member = getMember(email);
        extracted(commentDto);
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if(!member.getEmail().equals(comment.getMember().getEmail()))
            throw new IllegalArgumentException("댓글 작성자가 다릅니다.");
        comment.update(commentDto);
        return CommentResponseDto.builder()
                .id(comment.getId())
                .name((comment.getMember().getEmail()))
                .comment(comment.getComment_content())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }


    // 댓글 삭제
    public Long deleteComment(Long id, String email)  {
        Member member = getMember(email);
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if(!member.getEmail().equals(comment.getMember().getEmail()))
            throw new IllegalArgumentException("댓글 작성자가 다릅니다.");
        commentRepository.deleteById(id);
        return id;
    }

    // 댓글 목록 조회
    @Transactional
    public CommonResponseDto<?> getCommentList(Long postId) {
        Board board = boardService.isPresentBoard(postId);
        if (null == board) {
            return CommonResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        List<Comment> commentList = commentRepository.findAllByBoardId(postId);
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
        return CommonResponseDto.success(commentResponseDtoList);
    }
}
