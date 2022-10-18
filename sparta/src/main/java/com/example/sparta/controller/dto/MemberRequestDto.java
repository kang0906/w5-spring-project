package com.example.sparta.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class MemberRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,8}", message = "비밀번호는 4~8자 영문 대 소문자, 숫자, 특수문자를 모두 사용하세요.")
    private String password;

    public MemberRequestDto(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setEncodePwd(String encodePwd){
        this.password = encodePwd;
    }


}
