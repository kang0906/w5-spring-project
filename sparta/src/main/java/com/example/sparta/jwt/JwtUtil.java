package com.example.sparta.jwt;

import com.example.sparta.controller.dto.TokenDto;
import com.example.sparta.entity.RefreshToken;
import com.example.sparta.repository.RefreshTokenRepository;
import com.example.sparta.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";
    private static final Long ACCESS_TIME = 10 * 1000L;
    private static final Long REFRESH_TIME = 10000 * 1000L;

    @Value("${jwt.secret.key}") //application.properties에 선언해놓은 값을 가져온다
    private String secretKey;
    private Key key; //키 객체 필요 Key에서 HS256을 받음
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //암호화할 알고리즘 객체

    @PostConstruct // key객체을 초기화하기 위한 어노테이션
    public void init(){ //초기화
        byte[] bytes = Base64.getDecoder().decode(secretKey); //secretket를 decoding
        //Base64로 인코딩된 secretkey값이 decoding 되면서 byte배열에 들어감
        key = Keys.hmacShaKeyFor(bytes); //bytes값인 secretkey를 key에 초기화
    }

    // header에서 토큰을 가져오는 기능
    public String getHeaderToken(HttpServletRequest request, String type){ //type은 Access와 Refresh가 있다
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);
    }

    // 토큰 생성
    public TokenDto createAllToken(String email){
        return new TokenDto(createToken(email, "Access"), createToken(email, "Refresh"));
    }

    public String createToken(String email, String type){

        Date date = new Date();

        //type을 Access로 줬을 때는 ACCESS_TIME
        long time = type.equals("Access") ? ACCESS_TIME : REFRESH_TIME;

        //JWT를 만드는 과정
        return Jwts.builder()
                .setSubject(email) //
                .setExpiration(new Date(date.getTime() + time))
                .setIssuedAt(date) //토큰 발행 시간 정보
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    //토큰 검증
    public Boolean tokenValidation(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return false;
        }
    }

    // refreshToken 검증
    public Boolean refreshTokenValidation(String token){

        //1차 토큰 검증
        if(!tokenValidation(token)) return false;

        //DB에 저장한 토큰 비교
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberEmail(getEmailFromToken(token));

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String email){
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 email을 가져오는 기능
    public String getEmailFromToken(String token){
        return  Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}
