package com.example.sparta.service;

import com.example.sparta.controller.dto.LogInRequestDto;
import com.example.sparta.controller.dto.MemberRequestDto;
import com.example.sparta.controller.dto.MemberResponseDto;
import com.example.sparta.controller.dto.TokenDto;
import com.example.sparta.controller.response.CommonResponseDto;
import com.example.sparta.entity.Member;
import com.example.sparta.entity.RefreshToken;
import com.example.sparta.jwt.JwtUtil;
import com.example.sparta.repository.MemberRepository;
import com.example.sparta.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public CommonResponseDto signUp(MemberRequestDto memberRequestDto) {
        //email 중복 검사
        if (memberRepository.findByEmail(memberRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("중복 체크");
        }
        ;

        memberRequestDto.setEncodePwd(passwordEncoder.encode(memberRequestDto.getPassword()));
        Member member = new Member(memberRequestDto);

        memberRepository.save(member);

        return CommonResponseDto.success(new MemberResponseDto(member));
    }

    public CommonResponseDto logIn(LogInRequestDto logInRequestDto, HttpServletResponse response) {

        Member member = memberRepository.findByEmail(logInRequestDto.getEmail()).orElseThrow(
                () -> new RuntimeException("Not found Member")
        );

        if (!passwordEncoder.matches(logInRequestDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        //토큰 발급
        TokenDto tokenDto = jwtUtil.createAllToken(logInRequestDto.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberEmail(logInRequestDto.getEmail());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().update(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), logInRequestDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        return CommonResponseDto.success(new MemberResponseDto(member));
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {

        response.addHeader(JwtUtil.ACCESS_TOKEN,tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN,tokenDto.getRefreshToken());
    }
}
