package com.example.sparta.controller;

import com.example.sparta.controller.dto.LogInRequestDto;
import com.example.sparta.controller.dto.MemberRequestDto;
import com.example.sparta.controller.dto.MemberResponseDto;
import com.example.sparta.controller.response.CommonResponseDto;
import com.example.sparta.jwt.JwtUtil;
import com.example.sparta.security.UserDetailsImpl;
import com.example.sparta.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    //회원가입
    @PostMapping("api/member/signup")
    public CommonResponseDto signUp(@RequestBody @Valid MemberRequestDto memberRequestDto){
        return memberService.signUp(memberRequestDto);
    }

    @PostMapping("api/member/login")
    public CommonResponseDto logIn(@RequestBody @Valid LogInRequestDto logInRequestDto, HttpServletResponse response){
        return memberService.logIn(logInRequestDto, response);
    }

    @GetMapping("api/issue/token")
    public MemberResponseDto issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getMember().getEmail(), "Access"));
        return new MemberResponseDto();
    }
}
