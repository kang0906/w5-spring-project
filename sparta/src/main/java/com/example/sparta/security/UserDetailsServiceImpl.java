package com.example.sparta.security;

import com.example.sparta.entity.Member;
import com.example.sparta.repository.MemberRepository;
import com.example.sparta.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //Email값으로 Member를 가져옴
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Not Found Account")
        );

        //UserDetailsImpl에 Member를 담아줌
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setMember(member);
        return userDetails;
    }
}
