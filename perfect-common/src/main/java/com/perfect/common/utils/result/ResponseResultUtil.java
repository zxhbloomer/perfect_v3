package com.perfect.common.utils.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.common.constant.PerfectConstant;
import com.perfect.common.exception.ValidateCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * response返回值工具类
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
            ResultUtil.success(data)
        ));
    }

    public static void responseWriteError(
                                            HttpServletRequest request,
                                            HttpServletResponse response,
                                            Exception exception,
                                            int httpStatus
                                          ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = "";
        response.setContentType(PerfectConstant.JSON_UTF8);
        if(exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException){
            message = "用户名或密码错误";
            response.getWriter().write(objectMapper.writeValueAsString(
                    ResultUtil.error(HttpStatus.UNAUTHORIZED.value(),
                                    exception,
                                    exception.getMessage(),
                                    request)
                            )
            );
        }else if(exception instanceof ValidateCodeException){
            response.getWriter().write(objectMapper.writeValueAsString(
                    ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                    exception,
                                    exception.getMessage(),
                                    request)
            ));
        }else{
            response.getWriter().write(objectMapper.writeValueAsString(
                    ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                    exception,
                                    exception.getMessage(),
                                    request)
            ));
        }
        response.setStatus(httpStatus);
    }
}
