package com.example.sparta.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LogInRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public LogInRequestDto(String email, String password){
        this.email = email;
        this.password = password;
    }
}
