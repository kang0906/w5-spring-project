package com.example.sparta.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonResponseDto {
    private Boolean success;
    private Object data;

    public CommonResponseDto(Boolean success, Object data){
        this.success = success;
        this.data = data;
    }
}
