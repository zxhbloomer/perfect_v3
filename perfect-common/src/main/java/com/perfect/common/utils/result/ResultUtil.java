package com.perfect.common.utils.result;

import com.perfect.bean.pojo.JSONResult;
import com.perfect.common.utils.CommonUtil;
import com.perfect.common.utils.DateTimeUtil;
import com.perfect.common.utils.ExceptionUtil;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

public class ResultUtil {

    public static Object success(Object data) {
        return JSONResult.builder()
                .timestamp(DateTimeUtil.getSystemDateYYYYMMDDHHMMSS())
                .status(HttpStatus.OK.value())
                .message("调用成功")
                .path(CommonUtil.getRequest().getContextPath())
                .success(true)
                .data(data)
                .build();
    }

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

    public static Object error(Integer status, String error, Exception exception, String message, HttpServletRequest request) {

        return JSONResult
                .<String>builder()
                .timestamp(DateTimeUtil.getSystemDateYYYYMMDDHHMMSS())
                .status(status)
                .message(message)
                .path(request.getRequestURL().toString())
                .success(false)
                .data(ExceptionUtil.getException(exception))
                .build();
    }
}
