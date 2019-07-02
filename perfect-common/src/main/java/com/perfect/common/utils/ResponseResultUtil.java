package com.perfect.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.Enum.ResultEnum;
import com.perfect.exception.ValidateCodeException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseResultUtil {

//    public static void responseWriteError(ObjectMapper objectMapper,
//                                          HttpServletRequest request,
//                                          HttpServletResponse response,
//                                          AuthenticationException exception,
//                                          int httpStatus
//                                          ) throws IOException, ServletException {
//        responseWriteError(objectMapper,request,response,exception);
//        response.setStatus(httpStatus);
//
//    }

    public static void responseWriteError(ObjectMapper objectMapper,
                                                HttpServletRequest request,
                                               HttpServletResponse response,
                                               Exception exception,
                                          int httpStatus
                                          ) throws IOException, ServletException {
        String message = "";
        response.setContentType("application/json;charset=UTF-8");
        if(exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException){
            message = "用户名或密码错误";
            response.getWriter().write(objectMapper.writeValueAsString(
                    ResultUtil.error(ResultEnum.FAIL.getCode(),
                                    message,
                                    ExceptionUtil.getException(exception),
                                    exception.getMessage(),
                                    request)
                            )
            );
        }else if(exception instanceof ValidateCodeException){
            response.getWriter().write(objectMapper.writeValueAsString(
                    ResultUtil.error(ResultEnum.CODE_ERROT.getCode(),
                                    ResultEnum.CODE_ERROT.getMsg(),
                                    ExceptionUtil.getException(exception),
                                    exception.getMessage(),
                                    request)
            ));
        }else{
            response.getWriter().write(objectMapper.writeValueAsString(
                    ResultUtil.error(ResultEnum.FAIL.getCode(),
                                    ResultEnum.FAIL.getMsg(),
                                    ExceptionUtil.getException(exception),
                                    exception.getMessage(),
                                    request)
            ));
        }
        response.setStatus(httpStatus);
    }
}
