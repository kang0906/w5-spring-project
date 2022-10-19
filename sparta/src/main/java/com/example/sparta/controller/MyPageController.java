package com.example.sparta.controller;

import com.example.sparta.controller.response.CommonResponseDto;
import com.example.sparta.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/post")
    CommonResponseDto getMyPost(){
//        Long memberId = 0 // 로그인로직 구현후 memberId 입력해줄것
        String memberName = "testUser"; // todo : 로그인로직 구현후 username 입력해줄것
        return CommonResponseDto.success(myPageService.getMyBoard(memberName));
    }

    @GetMapping("/comment")
    CommonResponseDto getMyComment(){
        String memberName = "testUser"; // todo : 로그인로직 구현후 username 입력해줄것
        return CommonResponseDto.success(myPageService.getMyComment(memberName));
    }

    @GetMapping("/likes")
    CommonResponseDto getMyLikes(){
        String memberName = "testUser"; // todo : 로그인로직 구현후 username 입력해줄것
        return CommonResponseDto.success(myPageService.getMyLikes(memberName));
    }
}
