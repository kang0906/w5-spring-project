package com.example.sparta.controller;

import com.example.sparta.controller.response.CommonResponseDto;
import com.example.sparta.security.UserDetailsImpl;
import com.example.sparta.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/post")
    CommonResponseDto getMyPost( @AuthenticationPrincipal UserDetailsImpl userDetail){
//        Long memberId = 0 // 로그인로직 구현후 memberId 입력해줄것
        String memberName = userDetail.getMember().getName();
        return CommonResponseDto.success(myPageService.getMyBoard(memberName));
    }

    @GetMapping("/comment")
    CommonResponseDto getMyComment( @AuthenticationPrincipal UserDetailsImpl userDetail){
        String memberName = userDetail.getMember().getName();
        return CommonResponseDto.success(myPageService.getMyComment(memberName));
    }

    @GetMapping("/likes")
    CommonResponseDto getMyLikes( @AuthenticationPrincipal UserDetailsImpl userDetail){
        String memberName = userDetail.getMember().getName();
        return CommonResponseDto.success(myPageService.getMyLikes(memberName));
    }
}
