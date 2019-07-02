package com.zxh.utils;

import com.zxh.bean.pojo.JSONResult;

import javax.servlet.http.HttpServletRequest;

public class ResultUtil {

    public static Object success(Integer status, String message, String path, Object data) {
        return JSONResult.builder()
                .timestamp(DateTimeUtil.getSystemDateYYYYMMDDHHMMSS())
                .status(status)
                .message(message)
                .path(path)
                .success(true)
                .data(data)
                .build();

    }
//
//    public static Result success() {
//        return (Result) success(null);
//    }

    public static Object error(Integer status, String error, String exception, String message, HttpServletRequest request) {

        return JSONResult
                .<String>builder()
                .timestamp(DateTimeUtil.getSystemDateYYYYMMDDHHMMSS())
                .status(status)
                .message(message)
                .path(request.getRequestURL().toString())
                .success(false)
                .data(exception)
                .build();
    }
}
