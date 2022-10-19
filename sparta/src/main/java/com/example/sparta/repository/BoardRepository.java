package com.example.sparta.repository;

import com.example.sparta.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByMember_id(@Param(value = "member_id") Long memberId);

    List<Board> findAllByOrderByModifiedAtDesc();

}
