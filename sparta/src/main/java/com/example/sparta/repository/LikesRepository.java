package com.example.sparta.repository;

import com.example.sparta.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllByMember_id(@Param(value = "member_id") Long memberId);

    Optional<Likes> findByBoardIdAndMemberEmail(Long postId, String userEmail);
}
