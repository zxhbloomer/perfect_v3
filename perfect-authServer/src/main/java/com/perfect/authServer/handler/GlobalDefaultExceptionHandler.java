package com.perfect.authServer.handler;

import com.perfect.common.Enum.ResultEnum;
import com.perfect.common.utils.ExceptionUtil;
import com.perfect.common.utils.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object defaultExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e){
//        return ResponseExceptionBean.builder().code(500).msg(e.getMessage()).build();
       return ResultUtil.error(ResultEnum.FAIL.getCode(),
                e.getMessage(),
                ExceptionUtil.getException(e),
                e.getMessage(),
                request);
    }

}
