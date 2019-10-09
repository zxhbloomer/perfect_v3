package com.perfect.bean.result.utils.v1;

import javax.servlet.http.HttpServletRequest;

import com.perfect.bean.pojo.result.JsonResult;
import com.perfect.common.enumconfig.ResultEnum;
import org.springframework.http.HttpStatus;

import com.perfect.common.utils.CommonUtil;
import com.perfect.common.utils.DateTimeUtil;
import com.perfect.common.utils.ExceptionUtil;

/**
 * json返回值工具类
 * @author zxh
 */
public class ResultUtil {

    public static <T> JsonResult<T> OK(T data, String message) {
        return JsonResult.<T>builder()
            .timestamp(DateTimeUtil.getTime())
            .http_status(HttpStatus.OK.value())
            .code(ResultEnum.OK.getCode())
            .message(message)
            .path(CommonUtil.getRequest().getRequestURL().toString())
            .method(CommonUtil.getRequest().getMethod())
            .success(true)
            .data(data)
            .build();
    }

    /**
     * 无错误的返回
     * @param data
     * @param <T>
     * @return
     */
    public static <T>JsonResult<T> OK(T data, boolean json_null_out) {
        return JsonResult.<T>builder()
                .timestamp(DateTimeUtil.getTime())
                .http_status(HttpStatus.OK.value())
                .code(ResultEnum.OK.getCode())
                .message("调用成功")
                .path(CommonUtil.getRequest().getRequestURL().toString())
                .method(CommonUtil.getRequest().getMethod())
                .success(true)
                .json_null_out(json_null_out)
                .data(data)
                .build();
    }

    /**
     * 无错误的返回
     * @param data
     * @param <T>
     * @return
     */
    public static <T>JsonResult<T> OK(T data) {
        return ResultUtil.OK(data, false);
//        return JsonResult.<T>builder()
//            .timestamp(DateTimeUtil.getTime())
//            .http_status(HttpStatus.OK.value())
//            .code(ResultEnum.OK.getCode())
//            .message("调用成功")
//            .path(CommonUtil.getRequest().getRequestURL().toString())
//            .method(CommonUtil.getRequest().getMethod())
//            .success(true)
//            .json_null_out(false)
//            .data(data)
//            .build();
    }

    /**
     * 含code的无错误的返回
     * @param data
     * @param code
     * @param <T>
     * @return
     */
    public static <T>JsonResult<T> OK(T data, int code) {
        return JsonResult.<T>builder()
            .timestamp(DateTimeUtil.getTime())
            .http_status(HttpStatus.OK.value())
            .code(code)
            .message("调用成功")
            .path(CommonUtil.getRequest().getRequestURL().toString())
            .method(CommonUtil.getRequest().getMethod())
            .success(true)
            .data(data)
            .build();
    }

//    public static Object OK(Integer status, String message, String path, String method, Object data) {
//        return JSONResult.builder()
//                .timestamp(DateTimeUtil.getSystemDateYYYYMMDDHHMMSS())
//                .status(status)
//                .message(message)
//                .path(path)
//                .method(method)
//                .OK(true)
//                .data(data)
//                .build();
//
//    }
//
//    public static Result OK() {
//        return (Result) OK(null);
//    }

    public static <T>JsonResult<T> NG(Integer status, Exception exception, String message, HttpServletRequest request) {

        return JsonResult.<T>builder()
                .timestamp(DateTimeUtil.getTime())
                .http_status(status)
                .code(ResultEnum.FAIL.getCode())
                .message(message)
                .path(request.getRequestURL().toString())
                .method(request.getMethod())
                .success(false)
                .data((T) ExceptionUtil.getException(exception))
                .build();
    }
}
