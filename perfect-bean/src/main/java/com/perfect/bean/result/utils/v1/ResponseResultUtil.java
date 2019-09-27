package com.perfect.bean.result.utils.v1;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.common.constant.PerfectConstant;
import com.perfect.common.exception.ValidateCodeException;

/**
 * response返回值工具类
 * @author zxh
 */
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

    public static void responseWriteOK(Object data, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(
            ResultUtil.OK(data)
        ));
    }

    public static void responseWriteError(
                                            HttpServletRequest request,
                                            HttpServletResponse response,
                                            Exception exception,
                                            int httpStatus,
                                            String errorMessage
                                        ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(PerfectConstant.JSON_UTF8);
        if(exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException){
            response.getWriter().write(objectMapper.writeValueAsString(
                    ResultUtil.NG(HttpStatus.UNAUTHORIZED.value(),
                                    exception,
                                    errorMessage,
                                    request)
                            )
            );
        }else if(exception instanceof ValidateCodeException){
            response.getWriter().write(objectMapper.writeValueAsString(
                    ResultUtil.NG(httpStatus,
                                    exception,
                                    errorMessage,
                                    request)
            ));
        }else{
            response.getWriter().write(objectMapper.writeValueAsString(
                    ResultUtil.NG(httpStatus,
                                    exception,
                                    errorMessage,
                                    request)
            ));
        }

        response.setStatus(httpStatus);
    }
}
