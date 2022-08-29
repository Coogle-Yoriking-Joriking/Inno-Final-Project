package com.inno.coogle.global.common.response;

public class ApiUtils {
    public static <T> CommonResponse<T> success(int code, T result) {
        return new CommonResponse<>(code, result);
    }

}