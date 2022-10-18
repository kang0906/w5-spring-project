package com.example.sparta.repository;

import com.example.sparta.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMember_id(/*@Param(value = "member_id")*/ Long memberId);

}
