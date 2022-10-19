package com.example.sparta.controller.response;

import com.example.sparta.entity.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class CommonResponseDto<T> {
    private boolean success;
    private T data;
    private Error error;

    public static <T> CommonResponseDto<T> success(T data) {
        return new CommonResponseDto<>(true, data, null);
    }

    public static <T> CommonResponseDto<T> fail(String code, String message) {
        return new CommonResponseDto<>(false, null, new Error(code, message));
    }

}
