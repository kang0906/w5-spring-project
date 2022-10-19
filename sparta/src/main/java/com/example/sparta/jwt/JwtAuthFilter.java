package com.example.sparta.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    //JWT 인증을 하는 로직을 짬
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.getHeaderToken(request,"Access");
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");

        //accessToken 있는지 확인
        if(accessToken != null){
            //검증이 안됬다면 에러메세지 보내고 끝
            if(!jwtUtil.tokenValidation(accessToken)){
                System.out.println("JwtAuthFilter.doFilterInternal");
                return;
            }
            //검증이 완료되면 accessToken을 인증 객체에 넣기
            setAuthentication(jwtUtil.getEmailFromToken(accessToken));
        }else if(refreshToken != null){
            if(!jwtUtil.refreshTokenValidation(refreshToken)){
                System.out.println("JwtAuthFilter.doFilterInternal");
                return;
            }
            setAuthentication(jwtUtil.getEmailFromToken(refreshToken));
        }

        filterChain.doFilter(request, response); //filterChain을 하지 않으면 doFilter만 실행하고 Filter 끝! 즉, 다음 필터실행
    }

    public void setAuthentication(String email){
        Authentication authentication = jwtUtil.createAuthentication(email);
        //jwtUtil에서 인증객체 생성
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
